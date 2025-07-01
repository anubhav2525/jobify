CREATE TABLE
    users (
        id BIGSERIAL PRIMARY KEY,
        email VARCHAR(255) UNIQUE NOT NULL,
        password VARCHAR(255) NOT NULL,
        phone VARCHAR(20),
        first_name VARCHAR(100) NOT NULL,
        middle_name VARCHAR(100) NOT NULL,
        last_name VARCHAR(100) NOT NULL,
        profile_image_url VARCHAR(500),
        user_type VARCHAR(20) NOT NULL CHECK (
            user_type IN (
                'ADMIN',
                'COMPANY_OWNER',
                'RECRUITER',
                'CANDIDATE',
            )
        ),
        is_active BOOLEAN DEFAULT true,
        is_verified BOOLEAN DEFAULT false,
        email_verified_at TIMESTAMP,
        phone_verified_at TIMESTAMP,
        last_login_at TIMESTAMP,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE
    user_sessions (
        id BIGSERIAL PRIMARY KEY,
        user_id BIGINT REFERENCES users (id) ON DELETE CASCADE,
        device_info TEXT,
        ip_address INET,
        user_agent TEXT,
        expires_at TIMESTAMP NOT NULL,
        is_active BOOLEAN DEFAULT true,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE
    companies (
        id BIGSERIAL PRIMARY KEY,
        owner_id BIGINT REFERENCES users (id) ON DELETE SET NULL,
        company_name VARCHAR(255) NOT NULL,
        legal_name VARCHAR(255),
        company_slug VARCHAR(255) UNIQUE,
        description TEXT,
        website_url VARCHAR(500),
        banner_url VARCHAR(500),
        thumbnail_url VARCHAR(500),
        industry VARCHAR(100),
        company_size VARCHAR(50) CHECK (
            company_size IN (
                '1-10',
                '11-50',
                '51-200',
                '201-500',
                '501-1000',
                '1000+'
            )
        ),
        founded_year INTEGER,
        headquarters_address TEXT,
        headquarters_city VARCHAR(100),
        headquarters_state VARCHAR(100),
        headquarters_country VARCHAR(100),
        headquarters_postal_code VARCHAR(20),
        tax_id VARCHAR(100),
        registration_number VARCHAR(100),
        verification_status VARCHAR(20) DEFAULT 'pending' CHECK (
            verification_status IN ('pending', 'verified', 'rejected', 'suspended')
        ),
        verification_notes TEXT,
        is_active BOOLEAN DEFAULT true,
        subscription_plan VARCHAR(50) DEFAULT 'free',
        subscription_expires_at TIMESTAMP,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE
    company_documents (
        id BIGSERIAL PRIMARY KEY,
        company_id BIGINT REFERENCES companies (id) ON DELETE CASCADE,
        document_type VARCHAR(50) NOT NULL CHECK (
            document_type IN (
                'REGISTRATION_CERTIFICATE',
                'TAX_CERTIFICATE',
                'LICENSE',
                'OTHER'
            )
        ),
        document_name VARCHAR(255) NOT NULL,
        file_url VARCHAR(500) NOT NULL,
        file_size BIGINT,
        mime_type VARCHAR(100),
        verification_status VARCHAR(20) DEFAULT 'PENDING' CHECK (
            verification_status IN ('PENDING', 'APPROVED', 'REJECTED', 'UNDER_REVIEW')
        ),
        verified_by BIGINT REFERENCES users (id) ON DELETE SET NULL,
        verified_at TIMESTAMP,
        rejection_reason TEXT,
        uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE
    company_social_links (
        id BIGSERIAL PRIMARY KEY,
        company_id BIGINT REFERENCES companies (id) ON DELETE CASCADE,
        linkedin_url VARCHAR(500) NOT NULL,
        twitter_url VARCHAR(500) NOT NULL,
        facebook_url VARCHAR(500) NOT NULL,
        youtube_url VARCHAR(500) NOT NULL,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE
    company_recruiters (
        id BIGSERIAL PRIMARY KEY,
        company_id BIGINT REFERENCES companies (id) ON DELETE CASCADE,
        recruiter_id BIGINT REFERENCES users (id) ON DELETE CASCADE,
        role VARCHAR(50) DEFAULT 'RECRUITER' CHECK (
            role IN ('RECRUITER', 'SENIOR_RECRUITER', 'HIRING_MANAGER')
        ),
        invited_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        joined_at TIMESTAMP,
        is_active BOOLEAN DEFAULT true,
        created_by BIGINT REFERENCES users (id) ON DELETE SET NULL,
        UNIQUE (company_id, recruiter_id)
    );

CREATE TABLE
    job_categories (
        id BIGSERIAL PRIMARY KEY,
        name VARCHAR(100) NOT NULL,
        slug VARCHAR(100) UNIQUE NOT NULL,
        parent_id BIGINT REFERENCES job_categories (id) ON DELETE SET NULL,
        description TEXT,
        is_active BOOLEAN DEFAULT true,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- Main jobs table
CREATE TABLE
    jobs (
        id BIGSERIAL PRIMARY KEY,
        company_id BIGINT REFERENCES companies (id) ON DELETE CASCADE,
        posted_by BIGINT REFERENCES users (id) ON DELETE SET NULL,
        title VARCHAR(255) NOT NULL,
        slug VARCHAR(255),
        description TEXT NOT NULL,
        requirements TEXT,
        responsibilities TEXT,
        benefits TEXT,
        job_category_id BIGINT REFERENCES job_categories (id) ON DELETE SET NULL,
        employment_type VARCHAR(50) NOT NULL CHECK (
            employment_type IN (
                'full_time',
                'part_time',
                'contract',
                'temporary',
                'internship'
            )
        ),
        work_arrangement VARCHAR(50) NOT NULL CHECK (
            work_arrangement IN ('on_site', 'remote', 'hybrid')
        ),
        experience_level VARCHAR(50) NOT NULL CHECK (
            experience_level IN ('entry', 'mid', 'senior', 'lead', 'executive')
        ),
        min_experience_years INTEGER DEFAULT 0,
        max_experience_years INTEGER,
        min_salary DECIMAL(12, 2),
        max_salary DECIMAL(12, 2),
        salary_currency VARCHAR(3) DEFAULT 'INR' CHECK (
            salary_currency IN (
                'USD',
                'EUR',
                'INR',
                'GBP',
                'AUD',
                'CAD',
                'JPY',
                'CNY'
            )
        ),
        salary_type VARCHAR(20) CHECK (salary_type IN ('hourly', 'monthly', 'yearly')),
        location_city VARCHAR(100),
        location_state VARCHAR(100),
        location_country VARCHAR(100),
        is_salary_negotiable BOOLEAN DEFAULT false,
        application_deadline TIMESTAMP,
        total_positions INTEGER DEFAULT 1,
        status VARCHAR(20) DEFAULT 'draft' CHECK (
            status IN ('draft', 'active', 'paused', 'closed', 'expired')
        ),
        is_featured BOOLEAN DEFAULT false,
        is_urgent BOOLEAN DEFAULT false,
        view_count INTEGER DEFAULT 0,
        application_count INTEGER DEFAULT 0,
        ai_match_score DECIMAL(5, 2), -- Overall AI-generated job quality score
        seo_title VARCHAR(255),
        seo_description TEXT,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        published_at TIMESTAMP,
        expires_at TIMESTAMP
    );

CREATE TABLE
    job_skills (
        id BIGSERIAL PRIMARY KEY,
        job_id BIGINT REFERENCES jobs (id) ON DELETE CASCADE,
        skill_name VARCHAR(100) NOT NULL,
        is_required BOOLEAN DEFAULT false,
        proficiency_level VARCHAR(20) CHECK (
            proficiency_level IN ('beginner', 'intermediate', 'advanced', 'expert')
        ),
        weight DECIMAL(3, 2) DEFAULT 1.0, -- Weight for AI matching
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE
    job_questions (
        id BIGSERIAL PRIMARY KEY,
        job_id BIGINT REFERENCES jobs (id) ON DELETE CASCADE,
        question TEXT NOT NULL,
        question_type VARCHAR(20) NOT NULL CHECK (
            question_type IN (
                'text',
                'multiple_choice',
                'yes_no',
                'file_upload'
            )
        ),
        options JSONB, -- Store multiple choice options
        is_required BOOLEAN DEFAULT false,
        order_index INTEGER DEFAULT 0,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- Candidate profiles
CREATE TABLE
    candidate_profiles (
        id BIGSERIAL PRIMARY KEY,
        user_id BIGINT REFERENCES users (id) ON DELETE CASCADE UNIQUE,
        headline VARCHAR(255),
        summary TEXT,
        current_job_title VARCHAR(255),
        current_company VARCHAR(255),
        total_experience_years DECIMAL(4, 2),
        current_salary DECIMAL(12, 2),
        expected_salary DECIMAL(12, 2),
        salary_currency VARCHAR(3) DEFAULT 'INR' CHECK (
            salary_currency IN (
                'USD',
                'EUR',
                'INR',
                'GBP',
                'AUD',
                'CAD',
                'JPY',
                'CNY'
            )
        ),
        notice_period_days INTEGER,
        preferred_locations TEXT, -- Array of preferred locations
        willing_to_relocate BOOLEAN DEFAULT false,
        work_authorization VARCHAR(100),
        linkedin_url VARCHAR(500),
        github_url VARCHAR(500),
        portfolio_url VARCHAR(500),
        leetcode_profile_url VARCHAR(500),
        stackoverflow_profile_url VARCHAR(500),
        date_of_birth DATE,
        gender VARCHAR(20),
        nationality VARCHAR(100),
        address TEXT,
        city VARCHAR(100),
        state VARCHAR(100),
        country VARCHAR(100),
        postal_code VARCHAR(20),
        is_profile_public BOOLEAN DEFAULT true,
        is_open_to_work BOOLEAN DEFAULT true,
        profile_completion_score INTEGER DEFAULT 0,
        ai_profile_score DECIMAL(5, 2), -- AI-generated profile quality score
        last_activity_at TIMESTAMP,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- Candidate education
CREATE TABLE
    candidate_education (
        id BIGSERIAL PRIMARY KEY,
        candidate_id BIGINT REFERENCES candidate_profiles (id) ON DELETE CASCADE,
        institution_name VARCHAR(255) NOT NULL,
        degree_type VARCHAR(100) NOT NULL,
        field_of_study VARCHAR(255),
        grade_gpa VARCHAR(20),
        start_date DATE,
        end_date DATE,
        is_current BOOLEAN DEFAULT false,
        description TEXT,
        activities TEXT,
        order_index INTEGER DEFAULT 0,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- Candidate skills
CREATE TABLE
    candidate_skills (
        id BIGSERIAL PRIMARY KEY,
        candidate_id BIGINT REFERENCES candidate_profiles (id) ON DELETE CASCADE,
        skill_name VARCHAR(100) NOT NULL,
        proficiency_level VARCHAR(20) CHECK (
            proficiency_level IN ('beginner', 'intermediate', 'advanced', 'expert')
        ),
        years_of_experience DECIMAL(4, 2),
        is_primary BOOLEAN DEFAULT false,
        endorsements_count INTEGER DEFAULT 0,
        last_used_date DATE,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        UNIQUE (candidate_id, skill_name)
    );

-- Candidate certifications
CREATE TABLE
    candidate_certifications (
        id BIGSERIAL PRIMARY KEY,
        candidate_id BIGINT REFERENCES candidate_profiles (id) ON DELETE CASCADE,
        certification_name VARCHAR(255) NOT NULL,
        issuing_organization VARCHAR(255) NOT NULL,
        issue_date DATE,
        expiry_date DATE,
        credential_id VARCHAR(255),
        credential_url VARCHAR(500),
        verification_status VARCHAR(20) DEFAULT 'unverified' CHECK (
            verification_status IN ('verified', 'unverified', 'expired')
        ),
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE
    candidate_languages (
        id BIGSERIAL PRIMARY KEY,
        candidate_id BIGINT REFERENCES candidate_profiles (id) ON DELETE CASCADE,
        language_name VARCHAR(100) NOT NULL,
        proficiency_level VARCHAR(20) CHECK (
            proficiency_level IN (
                'basic',
                'conversational',
                'professional',
                'native'
            )
        ),
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        UNIQUE (candidate_id, language_name)
    );

-- Candidate resumes/documents
CREATE TABLE
    candidate_documents (
        id BIGSERIAL PRIMARY KEY,
        candidate_id BIGINT REFERENCES candidate_profiles (id) ON DELETE CASCADE,
        document_type VARCHAR(50) NOT NULL CHECK (
            document_type IN (
                'resume',
                'cover_letter',
                'portfolio',
                'certificate',
                'other'
            )
        ),
        file_name VARCHAR(255) NOT NULL,
        file_url VARCHAR(500) NOT NULL,
        file_size BIGINT,
        mime_type VARCHAR(100),
        is_primary BOOLEAN DEFAULT false,
        parsed_content TEXT, -- AI-parsed content
        ai_score DECIMAL(5, 2), -- AI quality score
        ats_compatibility_score DECIMAL(5, 2),
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- ========================================
-- APPLICATION MANAGEMENT TABLES
-- =============================================
-- Job applications
CREATE TABLE
    job_applications (
        id BIGSERIAL PRIMARY KEY,
        job_id BIGINT REFERENCES jobs (id) ON DELETE CASCADE,
        candidate_id BIGINT REFERENCES candidate_profiles (id) ON DELETE CASCADE,
        cover_letter BIGINT REFERENCES candidate_documents (id) ON DELETE SET NULL,
        resume_id BIGINT REFERENCES candidate_documents (id) ON DELETE SET NULL,
        status VARCHAR(20) DEFAULT 'applied' CHECK (
            status IN (
                'applied',
                'screening',
                'shortlisted',
                'interview_scheduled',
                'interviewed',
                'offer_made',
                'hired',
                'rejected',
                'withdrawn'
            )
        ),
        application_source VARCHAR(50) DEFAULT 'direct', -- direct, referral, job_board, etc.
        ai_match_score DECIMAL(5, 2), -- AI-calculated match score
        recruiter_rating INTEGER CHECK (recruiter_rating BETWEEN 1 AND 5),
        recruiter_notes TEXT,
        rejection_reason TEXT,
        salary_expectation DECIMAL(12, 2),
        availability_date DATE,
        applied_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        UNIQUE (job_id, candidate_id)
    );

-- Application question responses
CREATE TABLE
    application_responses (
        id BIGSERIAL PRIMARY KEY,
        application_id BIGINT REFERENCES job_applications (id) ON DELETE CASCADE,
        question_id BIGINT REFERENCES job_questions (id) ON DELETE CASCADE,
        response_text TEXT,
        response_file_url VARCHAR(500),
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- Application status history
CREATE TABLE
    application_status_history (
        id BIGSERIAL PRIMARY KEY,
        application_id BIGINT REFERENCES job_applications (id) ON DELETE CASCADE,
        from_status VARCHAR(20),
        to_status VARCHAR(20) NOT NULL,
        changed_by BIGINT REFERENCES users (id) ON DELETE SET NULL,
        notes TEXT,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- =============================================
-- INTERVIEW MANAGEMENT TABLES
-- =============================================
-- Interview schedules
CREATE TABLE
    interviews (
        id BIGSERIAL PRIMARY KEY,
        application_id BIGINT REFERENCES job_applications (id) ON DELETE CASCADE,
        interview_type VARCHAR(50) NOT NULL CHECK (
            interview_type IN (
                'phone',
                'video',
                'in_person',
                'technical',
                'panel'
            )
        ),
        interview_round INTEGER DEFAULT 1,
        scheduled_date TIMESTAMP NOT NULL,
        duration_minutes INTEGER DEFAULT 60,
        location VARCHAR(500), -- Physical location or video link
        meeting_link VARCHAR(500),
        meeting_id VARCHAR(100),
        meeting_password VARCHAR(100),
        status VARCHAR(20) DEFAULT 'scheduled' CHECK (
            status IN (
                'scheduled',
                'in_progress',
                'completed',
                'cancelled',
                'rescheduled'
            )
        ),
        candidate_confirmed BOOLEAN DEFAULT false,
        recruiter_confirmed BOOLEAN DEFAULT false,
        reminder_sent BOOLEAN DEFAULT false,
        instructions TEXT,
        created_by BIGINT REFERENCES users (id) ON DELETE SET NULL,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- Interview participants (interviewers)
CREATE TABLE
    interview_participants (
        id BIGSERIAL PRIMARY KEY,
        interview_id BIGINT REFERENCES interviews (id) ON DELETE CASCADE,
        user_id BIGINT REFERENCES users (id) ON DELETE CASCADE,
        role VARCHAR(50) DEFAULT 'interviewer' CHECK (
            role IN ('interviewer', 'observer', 'coordinator')
        ),
        confirmation_status VARCHAR(20) DEFAULT 'pending' CHECK (
            confirmation_status IN ('pending', 'confirmed', 'declined')
        ),
        joined_at TIMESTAMP,
        left_at TIMESTAMP,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- Interview feedback
CREATE TABLE
    interview_feedback (
        id BIGSERIAL PRIMARY KEY,
        interview_id BIGINT REFERENCES interviews (id) ON DELETE CASCADE,
        interviewer_id BIGINT REFERENCES users (id) ON DELETE CASCADE,
        overall_rating INTEGER CHECK (overall_rating BETWEEN 1 AND 5),
        technical_skills_rating INTEGER CHECK (technical_skills_rating BETWEEN 1 AND 5),
        communication_skills_rating INTEGER CHECK (communication_skills_rating BETWEEN 1 AND 5),
        cultural_fit_rating INTEGER CHECK (cultural_fit_rating BETWEEN 1 AND 5),
        recommendation VARCHAR(20) CHECK (
            recommendation IN (
                'strong_hire',
                'hire',
                'no_hire',
                'strong_no_hire'
            )
        ),
        strengths TEXT,
        weaknesses TEXT,
        detailed_feedback TEXT,
        additional_notes TEXT,
        is_final_feedback BOOLEAN DEFAULT false,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        UNIQUE (interview_id, interviewer_id)
    );

-- =============================================
-- AI & MATCHING TABLES
-- =============================================

-- AI matching scores between candidates and jobs
CREATE TABLE ai_job_matches (
    id BIGSERIAL PRIMARY KEY,
    job_id BIGINT REFERENCES jobs(id) ON DELETE CASCADE,
    candidate_id BIGINT REFERENCES candidate_profiles(id) ON DELETE CASCADE,
    overall_match_score DECIMAL(5,2) NOT NULL,
    skills_match_score DECIMAL(5,2),
    experience_match_score DECIMAL(5,2),
    location_match_score DECIMAL(5,2),
    salary_match_score DECIMAL(5,2),
    education_match_score DECIMAL(5,2),
    match_factors JSONB, -- Detailed breakdown of matching factors
    confidence_level DECIMAL(5,2),
    calculated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    model_version VARCHAR(50),
    UNIQUE(job_id, candidate_id)
);

-- AI recommendations for candidates
CREATE TABLE ai_candidate_recommendations (
    id BIGSERIAL PRIMARY KEY,
    candidate_id BIGINT REFERENCES candidate_profiles(id) ON DELETE CASCADE,
    recommendation_type VARCHAR(50) NOT NULL CHECK (recommendation_type IN ('job', 'skill', 'career_path', 'salary')),
    title VARCHAR(255) NOT NULL,
    description TEXT,
    recommendation_data JSONB,
    priority_score DECIMAL(5,2),
    is_viewed BOOLEAN DEFAULT false,
    is_dismissed BOOLEAN DEFAULT false,
    expires_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- AI recommendations for recruiters
CREATE TABLE ai_recruiter_recommendations (
    id BIGSERIAL PRIMARY KEY,
    recruiter_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    job_id BIGINT REFERENCES jobs(id) ON DELETE CASCADE,
    recommendation_type VARCHAR(50) NOT NULL CHECK (recommendation_type IN ('candidate', 'job_optimization', 'market_insight')),
    title VARCHAR(255) NOT NULL,
    description TEXT,
    recommendation_data JSONB,
    priority_score DECIMAL(5,2),
    is_viewed BOOLEAN DEFAULT false,
    is_dismissed BOOLEAN DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Skills master table for standardization
CREATE TABLE skills_master (
    id BIGSERIAL PRIMARY KEY,
    skill_name VARCHAR(100) UNIQUE NOT NULL,
    category VARCHAR(100),
    aliases TEXT[], -- Alternative names for the skill
    demand_score DECIMAL(5,2), -- Market demand score
    growth_trend DECIMAL(5,2), -- Growth trend percentage
    avg_salary_impact DECIMAL(5,2), -- Average salary impact percentage
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================================
-- COMMUNICATION TABLES
-- =============================================

-- Internal messaging system
CREATE TABLE messages (
    id BIGSERIAL PRIMARY KEY,
    sender_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    recipient_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    subject VARCHAR(255),
    content TEXT NOT NULL,
    message_type VARCHAR(50) DEFAULT 'direct' CHECK (message_type IN ('direct', 'application', 'interview', 'system')),
    related_application_id BIGINT REFERENCES job_applications(id) ON DELETE SET NULL,
    related_interview_id BIGINT REFERENCES interviews(id) ON DELETE SET NULL,
    is_read BOOLEAN DEFAULT false,
    read_at TIMESTAMP,
    parent_message_id BIGINT REFERENCES messages(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Notifications
CREATE TABLE notifications (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    notification_type VARCHAR(50) NOT NULL CHECK (notification_type IN ('application', 'interview', 'message', 'job_alert', 'system', 'ai_recommendation')),
    related_id BIGINT, -- Generic ID for related entity
    related_type VARCHAR(50), -- Type of related entity
    is_read BOOLEAN DEFAULT false,
    read_at TIMESTAMP,
    delivery_method VARCHAR(20) DEFAULT 'in_app' CHECK (delivery_method IN ('in_app', 'email', 'sms', 'push')),
    is_delivered BOOLEAN DEFAULT false,
    delivered_at TIMESTAMP,
    priority VARCHAR(20) DEFAULT 'normal' CHECK (priority IN ('low', 'normal', 'high', 'urgent')),
    expires_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Email templates
CREATE TABLE email_templates (
    id BIGSERIAL PRIMARY KEY,
    template_name VARCHAR(100) UNIQUE NOT NULL,
    template_type VARCHAR(50) NOT NULL,
    subject VARCHAR(255) NOT NULL,
    html_content TEXT NOT NULL,
    text_content TEXT,
    variables JSONB, -- Template variables
    is_active BOOLEAN DEFAULT true,
    created_by BIGINT REFERENCES users(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================================
-- ANALYTICS & REPORTING TABLES
-- =============================================

-- Job analytics
CREATE TABLE job_analytics (
    id BIGSERIAL PRIMARY KEY,
    job_id BIGINT REFERENCES jobs(id) ON DELETE CASCADE,
    metric_date DATE NOT NULL,
    views_count INTEGER DEFAULT 0,
    applications_count INTEGER DEFAULT 0,
    unique_visitors INTEGER DEFAULT 0,
    conversion_rate DECIMAL(5,2),
    avg_time_on_page INTEGER, -- in seconds
    bounce_rate DECIMAL(5,2),
    source_breakdown JSONB, -- Views by source
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(job_id, metric_date)
);

-- Company analytics
CREATE TABLE company_analytics (
    id BIGSERIAL PRIMARY KEY,
    company_id BIGINT REFERENCES companies(id) ON DELETE CASCADE,
    metric_date DATE NOT NULL,
    active_jobs INTEGER DEFAULT 0,
    total_applications INTEGER DEFAULT 0,
    profile_views INTEGER DEFAULT 0,
    time_to_hire_avg DECIMAL(8,2), -- in days
    cost_per_hire DECIMAL(12,2),
    application_to_hire_ratio DECIMAL(5,2),
    recruiter_activity_score DECIMAL(5,2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(company_id, metric_date)
);

-- Platform analytics
CREATE TABLE platform_analytics (
    id BIGSERIAL PRIMARY KEY,
    metric_date DATE NOT NULL,
    total_users INTEGER DEFAULT 0,
    new_users_today INTEGER DEFAULT 0,
    active_users_today INTEGER DEFAULT 0,
    total_jobs INTEGER DEFAULT 0,
    new_jobs_today INTEGER DEFAULT 0,
    total_applications INTEGER DEFAULT 0,
    new_applications_today INTEGER DEFAULT 0,
    total_hires INTEGER DEFAULT 0,
    new_hires_today INTEGER DEFAULT 0,
    revenue_today DECIMAL(12,2) DEFAULT 0,
    avg_session_duration INTEGER, -- in minutes
    bounce_rate DECIMAL(5,2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(metric_date)
);

-- User activity tracking
CREATE TABLE user_activity_logs (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE SET NULL,
    activity_type VARCHAR(50) NOT NULL,
    activity_description TEXT,
    related_entity_type VARCHAR(50),
    related_entity_id BIGINT,
    ip_address INET,
    user_agent TEXT,
    session_id VARCHAR(255),
    metadata JSONB,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================================
-- SUBSCRIPTION & BILLING TABLES
-- =============================================

-- Subscription plans
CREATE TABLE subscription_plans (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    slug VARCHAR(100) UNIQUE NOT NULL,
    description TEXT,
    price_monthly DECIMAL(10,2),
    price_yearly DECIMAL(10,2),
    currency VARCHAR(3) DEFAULT 'USD',
    max_job_posts INTEGER,
    max_applications_per_job INTEGER,
    max_recruiters INTEGER,
    features JSONB, -- List of features included
    is_active BOOLEAN DEFAULT true,
    is_popular BOOLEAN DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Company subscriptions
CREATE TABLE company_subscriptions (
    id BIGSERIAL PRIMARY KEY,
    company_id BIGINT REFERENCES companies(id) ON DELETE CASCADE,
    plan_id BIGINT REFERENCES subscription_plans(id) ON DELETE SET NULL,
    billing_cycle VARCHAR(20) CHECK (billing_cycle IN ('monthly', 'yearly')),
    status VARCHAR(20) DEFAULT 'active' CHECK (status IN ('active', 'cancelled', 'expired', 'suspended')),
    current_period_start TIMESTAMP NOT NULL,
    current_period_end TIMESTAMP NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    currency VARCHAR(3) DEFAULT 'USD',
    stripe_subscription_id VARCHAR(255),
    trial_end TIMESTAMP,
    cancelled_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Billing history
CREATE TABLE billing_transactions (
    id BIGSERIAL PRIMARY KEY,
    company_id BIGINT REFERENCES companies(id) ON DELETE CASCADE,
    subscription_id BIGINT REFERENCES company_subscriptions(id) ON DELETE SET NULL,
    transaction_type VARCHAR(50) NOT NULL CHECK (transaction_type IN ('payment', 'refund', 'chargeback', 'adjustment')),
    amount DECIMAL(10,2) NOT NULL,
    currency VARCHAR(3) DEFAULT 'USD',
    status VARCHAR(20) NOT NULL CHECK (status IN ('pending', 'completed', 'failed', 'cancelled')),
    payment_method VARCHAR(50),
    stripe_payment_intent_id VARCHAR(255),
    stripe_charge_id VARCHAR(255),
    invoice_number VARCHAR(100),
    description TEXT,
    processed_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================================
-- SYSTEM CONFIGURATION TABLES
-- =============================================

-- System settings
CREATE TABLE system_settings (
    id BIGSERIAL PRIMARY KEY,
    setting_key VARCHAR(100) UNIQUE NOT NULL,
    setting_value TEXT,
    setting_type VARCHAR(50) DEFAULT 'string' CHECK (setting_type IN ('string', 'number', 'boolean', 'json')),
    description TEXT,
    is_public BOOLEAN DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Audit logs
CREATE TABLE audit_logs (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE SET NULL,
    action VARCHAR(100) NOT NULL,
    entity_type VARCHAR(50) NOT NULL,
    entity_id BIGINT,
    old_values JSONB,
    new_values JSONB,
    ip_address INET,
    user_agent TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- File uploads tracking
CREATE TABLE file_uploads (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE SET NULL,
    original_filename VARCHAR(255) NOT NULL,
    stored_filename VARCHAR(255) NOT NULL,
    file_path VARCHAR(500) NOT NULL,
    file_size BIGINT NOT NULL,
    mime_type VARCHAR(100) NOT NULL,
    file_hash VARCHAR(255),
    upload_source VARCHAR(50),
    related_entity_type VARCHAR(50),
    related_entity_id BIGINT,
    is_processed BOOLEAN DEFAULT false,
    processing_status VARCHAR(50),
    metadata JSONB,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================================
-- SAVED ITEMS & PREFERENCES
-- =============================================

-- Saved jobs by candidates
CREATE TABLE saved_jobs (
    id BIGSERIAL PRIMARY KEY,
    candidate_id BIGINT REFERENCES candidate_profiles(id) ON DELETE CASCADE,
    job_id BIGINT REFERENCES jobs(id) ON DELETE CASCADE,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(candidate_id, job_id)
);

-- Saved candidates by recruiters
CREATE TABLE saved_candidates (
    id BIGSERIAL PRIMARY KEY,
    recruiter_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    candidate_id BIGINT REFERENCES candidate_profiles(id) ON DELETE CASCADE,
    job_id BIGINT REFERENCES jobs(id) ON DELETE SET NULL,
    notes TEXT,
    tags TEXT[],
    rating INTEGER CHECK (rating BETWEEN 1 AND 5),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(recruiter_id, candidate_id)
);

-- Job alerts for candidates
CREATE TABLE job_alerts (
    id BIGSERIAL PRIMARY KEY,
    candidate_id BIGINT REFERENCES candidate_profiles(id) ON DELETE CASCADE,
    alert_name VARCHAR(255) NOT NULL,
    keywords TEXT[],
    location VARCHAR(255),
    job_category_id BIGINT REFERENCES job_categories(id) ON DELETE SET NULL,
    employment_type VARCHAR(50),
    work_arrangement VARCHAR(50),
    min_salary DECIMAL(12,2),
    max_salary DECIMAL(12,2),
    experience_level VARCHAR(50),
    company_size VARCHAR(50),
    is_active BOOLEAN DEFAULT true,
    frequency VARCHAR(20) DEFAULT 'daily' CHECK (frequency IN ('immediate', 'daily', 'weekly')),
    last_sent_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Candidate alerts for recruiters
CREATE TABLE candidate_alerts (
    id BIGSERIAL PRIMARY KEY,
    recruiter_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    alert_name VARCHAR(255) NOT NULL,
    skills TEXT[],
    location VARCHAR(255),
    min_experience DECIMAL(4,2),
    max_experience DECIMAL(4,2),
    education_level VARCHAR(100),
    current_company VARCHAR(255),
    availability VARCHAR(50),
    is_active BOOLEAN DEFAULT true,
    frequency VARCHAR(20) DEFAULT 'daily' CHECK (frequency IN ('immediate', 'daily', 'weekly')),
    last_sent_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- User preferences
CREATE TABLE user_preferences (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE UNIQUE,
    email_notifications JSONB DEFAULT '{}',
    push_notifications JSONB DEFAULT '{}',
    sms_notifications JSONB DEFAULT '{}',
    privacy_settings JSONB DEFAULT '{}',
    ui_preferences JSONB DEFAULT '{}',
    language VARCHAR(10) DEFAULT 'en',
    timezone VARCHAR(50) DEFAULT 'UTC',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================================
-- SUPPORT & HELP DESK TABLES
-- =============================================

-- Support tickets
CREATE TABLE support_tickets (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE SET NULL,
    ticket_number VARCHAR(20) UNIQUE NOT NULL,
    subject VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    category VARCHAR(50) NOT NULL CHECK (category IN ('technical', 'billing', 'account', 'feature_request', 'bug_report', 'general')),
    priority VARCHAR(20) DEFAULT 'medium' CHECK (priority IN ('low', 'medium', 'high', 'urgent')),
    status VARCHAR(20) DEFAULT 'open' CHECK (status IN ('open', 'in_progress', 'resolved', 'closed', 'cancelled')),
    assigned_to BIGINT REFERENCES users(id) ON DELETE SET NULL,
    resolution TEXT,
    satisfaction_rating INTEGER CHECK (satisfaction_rating BETWEEN 1 AND 5),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    resolved_at TIMESTAMP,
    closed_at TIMESTAMP
);

-- Support ticket messages
CREATE TABLE support_ticket_messages (
    id BIGSERIAL PRIMARY KEY,
    ticket_id BIGINT REFERENCES support_tickets(id) ON DELETE CASCADE,
    sender_id BIGINT REFERENCES users(id) ON DELETE SET NULL,
    message TEXT NOT NULL,
    is_internal BOOLEAN DEFAULT false,
    attachments JSONB,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- FAQ categories and items
CREATE TABLE faq_categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    slug VARCHAR(255) UNIQUE NOT NULL,
    description TEXT,
    order_index INTEGER DEFAULT 0,
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE faq_items (
    id BIGSERIAL PRIMARY KEY,
    category_id BIGINT REFERENCES faq_categories(id) ON DELETE CASCADE,
    question TEXT NOT NULL,
    answer TEXT NOT NULL,
    order_index INTEGER DEFAULT 0,
    view_count INTEGER DEFAULT 0,
    helpful_count INTEGER DEFAULT 0,
    not_helpful_count INTEGER DEFAULT 0,
    is_active BOOLEAN DEFAULT true,
    created_by BIGINT REFERENCES users(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================================
-- REFERRAL & REWARDS TABLES
-- =============================================

-- Referral program
CREATE TABLE referrals (
    id BIGSERIAL PRIMARY KEY,
    referrer_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    referred_email VARCHAR(255) NOT NULL,
    referred_user_id BIGINT REFERENCES users(id) ON DELETE SET NULL,
    referral_code VARCHAR(50) UNIQUE NOT NULL,
    referral_type VARCHAR(20) NOT NULL CHECK (referral_type IN ('candidate', 'company', 'recruiter')),
    status VARCHAR(20) DEFAULT 'pending' CHECK (status IN ('pending', 'completed', 'expired', 'cancelled')),
    reward_amount DECIMAL(10,2),
    reward_currency VARCHAR(3) DEFAULT 'USD',
    reward_status VARCHAR(20) DEFAULT 'pending' CHECK (reward_status IN ('pending', 'approved', 'paid', 'cancelled')),
    completion_date TIMESTAMP,
    expiry_date TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Reward transactions
CREATE TABLE reward_transactions (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    transaction_type VARCHAR(50) NOT NULL CHECK (transaction_type IN ('referral_bonus', 'signup_bonus', 'activity_reward', 'penalty', 'adjustment')),
    amount DECIMAL(10,2) NOT NULL,
    currency VARCHAR(3) DEFAULT 'USD',
    description TEXT,
    related_referral_id BIGINT REFERENCES referrals(id) ON DELETE SET NULL,
    status VARCHAR(20) DEFAULT 'pending' CHECK (status IN ('pending', 'approved', 'paid', 'cancelled')),
    processed_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================================
-- ASSESSMENT & TESTING TABLES
-- =============================================

-- Assessment categories
CREATE TABLE assessment_categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    icon_url VARCHAR(500),
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Assessments/Tests
CREATE TABLE assessments (
    id BIGSERIAL PRIMARY KEY,
    category_id BIGINT REFERENCES assessment_categories(id) ON DELETE SET NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    instructions TEXT,
    duration_minutes INTEGER,
    total_questions INTEGER,
    passing_score DECIMAL(5,2),
    difficulty_level VARCHAR(20) CHECK (difficulty_level IN ('beginner', 'intermediate', 'advanced')),
    skills_covered TEXT[],
    is_active BOOLEAN DEFAULT true,
    created_by BIGINT REFERENCES users(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Assessment questions
CREATE TABLE assessment_questions (
    id BIGSERIAL PRIMARY KEY,
    assessment_id BIGINT REFERENCES assessments(id) ON DELETE CASCADE,
    question_text TEXT NOT NULL,
    question_type VARCHAR(20) NOT NULL CHECK (question_type IN ('multiple_choice', 'true_false', 'text', 'code', 'essay')),
    options JSONB, -- For multiple choice questions
    correct_answer TEXT,
    explanation TEXT,
    points INTEGER DEFAULT 1,
    difficulty VARCHAR(20) CHECK (difficulty IN ('easy', 'medium', 'hard')),
    order_index INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Candidate assessment attempts
CREATE TABLE candidate_assessments (
    id BIGSERIAL PRIMARY KEY,
    candidate_id BIGINT REFERENCES candidate_profiles(id) ON DELETE CASCADE,
    assessment_id BIGINT REFERENCES assessments(id) ON DELETE CASCADE,
    application_id BIGINT REFERENCES job_applications(id) ON DELETE SET NULL,
    status VARCHAR(20) DEFAULT 'not_started' CHECK (status IN ('not_started', 'in_progress', 'completed', 'expired', 'cancelled')),
    score DECIMAL(5,2),
    percentage_score DECIMAL(5,2),
    time_taken_minutes INTEGER,
    started_at TIMESTAMP,
    completed_at TIMESTAMP,
    expires_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Assessment responses
CREATE TABLE assessment_responses (
    id BIGSERIAL PRIMARY KEY,
    candidate_assessment_id BIGINT REFERENCES candidate_assessments(id) ON DELETE CASCADE,
    question_id BIGINT REFERENCES assessment_questions(id) ON DELETE CASCADE,
    response_text TEXT,
    is_correct BOOLEAN,
    points_earned INTEGER DEFAULT 0,
    time_taken_seconds INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================================
-- VIDEO INTERVIEW TABLES
-- =============================================

-- Video interview sessions
CREATE TABLE video_interviews (
    id BIGSERIAL PRIMARY KEY,
    interview_id BIGINT REFERENCES interviews(id) ON DELETE CASCADE,
    room_id VARCHAR(255) UNIQUE NOT NULL,
    platform VARCHAR(50) DEFAULT 'webrtc' CHECK (platform IN ('webrtc', 'zoom', 'teams', 'meet')),
    session_token VARCHAR(500),
    recording_url VARCHAR(500),
    recording_duration INTEGER, -- in seconds
    session_data JSONB, -- Platform-specific session data
    quality_metrics JSONB, -- Connection quality, audio/video metrics
    started_at TIMESTAMP,
    ended_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Video interview analytics (AI-powered)
CREATE TABLE video_interview_analytics (
    id BIGSERIAL PRIMARY KEY,
    video_interview_id BIGINT REFERENCES video_interviews(id) ON DELETE CASCADE,
    candidate_id BIGINT REFERENCES candidate_profiles(id) ON DELETE CASCADE,
    speech_analysis JSONB, -- Speech patterns, pace, clarity
    facial_analysis JSONB, -- Facial expressions, eye contact
    engagement_score DECIMAL(5,2),
    confidence_score DECIMAL(5,2),
    communication_score DECIMAL(5,2),
    overall_score DECIMAL(5,2),
    key_moments JSONB, -- Timestamps of important moments
    transcription TEXT,
    sentiment_analysis JSONB,
    processed_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================================
-- MARKETPLACE & INTEGRATIONS
-- =============================================

-- Third-party integrations
CREATE TABLE integrations (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    slug VARCHAR(255) UNIQUE NOT NULL,
    description TEXT,
    category VARCHAR(100),
    api_endpoint VARCHAR(500),
    auth_type VARCHAR(50) CHECK (auth_type IN ('oauth', 'api_key', 'basic_auth', 'jwt')),
    configuration JSONB,
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Company integrations
CREATE TABLE company_integrations (
    id BIGSERIAL PRIMARY KEY,
    company_id BIGINT REFERENCES companies(id) ON DELETE CASCADE,
    integration_id BIGINT REFERENCES integrations(id) ON DELETE CASCADE,
    configuration JSONB,
    credentials JSONB, -- Encrypted credentials
    is_active BOOLEAN DEFAULT true,
    last_sync_at TIMESTAMP,
    sync_status VARCHAR(50),
    error_log TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(company_id, integration_id)
);

-- API keys for third-party access
CREATE TABLE api_keys (
    id BIGSERIAL PRIMARY KEY,
    company_id BIGINT REFERENCES companies(id) ON DELETE CASCADE,
    key_name VARCHAR(255) NOT NULL,
    api_key VARCHAR(255) UNIQUE NOT NULL,
    api_secret VARCHAR(255),
    permissions JSONB,
    rate_limit INTEGER DEFAULT 1000,
    is_active BOOLEAN DEFAULT true,
    last_used_at TIMESTAMP,
    expires_at TIMESTAMP,
    created_by BIGINT REFERENCES users(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- API usage tracking
CREATE TABLE api_usage (
    id BIGSERIAL PRIMARY KEY,
    api_key_id BIGINT REFERENCES api_keys(id) ON DELETE CASCADE,
    endpoint VARCHAR(255) NOT NULL,
    method VARCHAR(10) NOT NULL,
    request_count INTEGER DEFAULT 1,
    response_time_ms INTEGER,
    status_code INTEGER,
    error_message TEXT,
    usage_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================================
-- CONTENT MANAGEMENT
-- =============================================

-- Blog/Content categories
CREATE TABLE content_categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    slug VARCHAR(255) UNIQUE NOT NULL,
    description TEXT,
    parent_id BIGINT REFERENCES content_categories(id) ON DELETE SET NULL,
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Blog posts and articles
CREATE TABLE content_posts (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    slug VARCHAR(255) UNIQUE NOT NULL,
    excerpt TEXT,
    content TEXT NOT NULL,
    featured_image_url VARCHAR(500),
    category_id BIGINT REFERENCES content_categories(id) ON DELETE SET NULL,
    author_id BIGINT REFERENCES users(id) ON DELETE SET NULL,
    status VARCHAR(20) DEFAULT 'draft' CHECK (status IN ('draft', 'published', 'archived')),
    view_count INTEGER DEFAULT 0,
    like_count INTEGER DEFAULT 0,
    comment_count INTEGER DEFAULT 0,
    seo_title VARCHAR(255),
    seo_description TEXT,
    tags TEXT[],
    published_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);