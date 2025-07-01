# 🚀 AI-Powered Job Platform

> A comprehensive job matching platform connecting candidates with opportunities through intelligent AI-driven features.

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Getting Started](#getting-started)
- [Project Structure](#project-structure)
- [API Documentation](#api-documentation)
- [Deployment](#deployment)
- [Contributing](#contributing)
- [License](#license)

## 🎯 Overview

This job platform is a modern, AI-powered recruitment solution that facilitates seamless job posting, candidate matching, and hiring processes. The platform serves three distinct user roles: **Admin**, **Company/Recruiter**, and **Candidate**, each with tailored features and capabilities.

### Key Highlights

- **🤖 AI-Powered Matching**: Intelligent job-candidate matching with compatibility scoring
- **📊 Advanced Analytics**: Comprehensive recruitment metrics and insights
- **🔒 Secure Authentication**: JWT-based authentication with role-based access control
- **📱 Responsive Design**: Modern UI with mobile-first approach
- **⚡ Real-time Features**: Live notifications and messaging system
- **🎨 Modern UI/UX**: Built with Tailwind CSS and shadcn/ui components

## ✨ Features

### 👑 Admin Panel
- **System Management**
    - Platform-wide analytics and monitoring
    - User management and support ticketing
    - Company verification and document management
    - Content moderation and compliance monitoring
    - Subscription and payment processing oversight

- **Advanced Analytics**
    - Usage statistics and engagement metrics
    - Revenue tracking and financial reporting
    - Geographic distribution analysis
    - Predictive market trend analysis

### 🏢 Company/Recruiter Features
- **Company Profile Management**
    - Comprehensive company profiles with media uploads
    - Social media integration and verification
    - Team management and recruiter permissions

- **Job Management**
    - Advanced job posting with templates
    - Bulk operations and performance analytics
    - AI-powered job description optimization
    - Competitive analysis and market intelligence

- **Candidate Management**
    - Advanced search and filtering capabilities
    - AI-powered candidate recommendations
    - Automated screening and scoring
    - Interview scheduling with calendar integration

- **Recruitment Analytics**
    - Time-to-hire and conversion metrics
    - Source effectiveness analysis
    - Team performance tracking
    - Hiring funnel optimization

### 👤 Candidate Features
- **Profile Management**
    - Comprehensive professional profiles
    - AI-powered resume builder with ATS-friendly templates
    - Skills assessment and certification tracking
    - Portfolio and media uploads

- **Job Discovery**
    - Intelligent job recommendations
    - Advanced search with 20+ filters
    - Saved searches and job alerts
    - Salary insights and market analysis

- **Application Management**
    - One-click applications with status tracking
    - Interview scheduling and preparation resources
    - Performance feedback and career guidance
    - Networking and mentorship opportunities

### 🤖 AI-Powered Features
- **Smart Matching Algorithm**
    - Skills compatibility analysis
    - Experience level assessment
    - Location and salary preference matching
    - Career progression alignment

- **Resume Intelligence**
    - Automated parsing and standardization
    - Quality scoring and optimization suggestions
    - ATS compatibility analysis
    - Keyword optimization

- **Predictive Analytics**
    - Hiring success probability
    - Interview outcome prediction
    - Salary negotiation insights
    - Market trend forecasting

## 🛠️ Tech Stack

### Backend
- **Framework**: Spring Boot 3.5.0 (Java 21)
- **Database**: PostgreSQL with JPA/Hibernate
- **Security**: Spring Security + JWT Authentication
- **Communication**: WebSocket for real-time features
- **Email**: Spring Mail with Thymeleaf templates
- **Validation**: Spring Boot Validation
- **Build Tool**: Maven

### Frontend
- **Framework**: Next.js 15.3.2 with React 19
- **Styling**: Tailwind CSS 4.0 with custom animations
- **UI Components**: Radix UI + shadcn/ui
- **Forms**: React Hook Form + Zod validation
- **Charts**: Recharts for data visualization
- **Animations**: Framer Motion
- **Themes**: Next Themes for dark/light mode
- **Notifications**: Sonner for toast notifications

### Key Libraries & Tools
- **Date Handling**: date-fns, React Day Picker
- **Carousels**: Embla Carousel React
- **Icons**: Lucide React
- **State Management**: React hooks
- **Type Safety**: TypeScript
- **Code Quality**: ESLint

## 🏗️ Architecture

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Next.js 15    │    │  Spring Boot    │    │   PostgreSQL    │
│   (Frontend)    │◄──►│   (Backend)     │◄──►│   (Database)    │
│                 │    │                 │    │                 │
│ • React 19      │    │ • REST APIs     │    │ • JPA/Hibernate │
│ • Tailwind CSS  │    │ • WebSocket     │    │ • Data Storage  │
│ • shadcn/ui     │    │ • JWT Auth      │    │ • Relationships │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

### Security Architecture
- JWT token-based authentication
- Role-based access control (RBAC)
- Refresh token rotation
- CORS configuration
- Input validation and sanitization
- SQL injection prevention

## 🚀 Getting Started

### Prerequisites
- **Java**: JDK 21 or higher
- **Node.js**: 18.0 or higher
- **PostgreSQL**: 14.0 or higher
- **Maven**: 3.8 or higher
- **npm/yarn**: Latest version

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/job-platform.git
   cd job-platform
   ```

2. **Backend Setup**
   ```bash
   cd server
   
   # Configure database in application.properties
   cp src/main/resources/application.properties.example src/main/resources/application.properties
   
   # Install dependencies and run
   mvn clean install
   mvn spring-boot:run
   ```

3. **Frontend Setup**
   ```bash
   cd client
   
   # Install dependencies
   npm install
   
   # Configure environment variables
   cp .env.example .env.local
   
   # Run development server
   npm run dev
   ```

4. **Database Setup**
   ```sql
   -- Create database
   CREATE DATABASE job_platform;
   
   -- Create user (optional)
   CREATE USER job_user WITH PASSWORD 'your_password';
   GRANT ALL PRIVILEGES ON DATABASE job_platform TO job_user;
   ```

### Environment Configuration

**Backend (application.properties)**
```properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/job_platform
spring.datasource.username=your_username
spring.datasource.password=your_password

# JWT Configuration
jwt.secret=your_jwt_secret_key
jwt.expiration=86400000

# Email Configuration
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your_email@gmail.com
spring.mail.password=your_app_password
```

**Frontend (.env.local)**
```env
NEXT_PUBLIC_API_URL=http://localhost:8080/api
NEXT_PUBLIC_WS_URL=ws://localhost:8080/ws
```

## 📁 Project Structure

```
job-platform/
├── server/                          # Spring Boot Backend
│   ├── src/main/java/com/naukari/
│   │   ├── config/                  # Configuration classes
│   │   ├── controller/              # REST Controllers
│   │   ├── dto/                     # Data Transfer Objects
│   │   ├── entity/                  # JPA Entities
│   │   ├── repository/              # Data Repositories
│   │   ├── service/                 # Business Logic
│   │   ├── security/                # Security Configuration
│   │   └── util/                    # Utility classes
│   └── src/main/resources/
│       ├── application.properties   # App Configuration
│       └── templates/               # Email Templates
│
├── client/                          # Next.js Frontend
│   ├── app/                         # App Router (Next.js 13+)
│   │   ├── (auth)/                  # Authentication routes
│   │   ├── admin/                   # Admin panel
│   │   ├── company/                 # Company dashboard
│   │   ├── candidate/               # Candidate dashboard
│   │   └── api/                     # API routes
│   ├── components/                  # React Components
│   │   ├── ui/                      # shadcn/ui components
│   │   ├── forms/                   # Form components
│   │   ├── charts/                  # Chart components
│   │   └── layout/                  # Layout components
│   ├── lib/                         # Utility functions
│   ├── hooks/                       # Custom React hooks
│   └── types/                       # TypeScript definitions
│
└── docs/                            # Documentation
    ├── api/                         # API Documentation
    ├── deployment/                  # Deployment guides
    └── development/                 # Development guides
```

## 📚 API Documentation

### Authentication Endpoints
```
POST   /api/auth/register           # User registration
POST   /api/auth/login              # User login
POST   /api/auth/refresh            # Refresh token
POST   /api/auth/logout             # User logout
POST   /api/auth/forgot-password    # Password reset
```

### Job Management
```
GET    /api/jobs                    # Get all jobs
POST   /api/jobs                    # Create job
GET    /api/jobs/{id}               # Get job by ID
PUT    /api/jobs/{id}               # Update job
DELETE /api/jobs/{id}               # Delete job
GET    /api/jobs/search             # Search jobs
```

### Candidate Management
```
GET    /api/candidates              # Get candidates
GET    /api/candidates/{id}         # Get candidate profile
PUT    /api/candidates/{id}         # Update candidate
GET    /api/candidates/search       # Search candidates
POST   /api/candidates/apply        # Apply to job
```

### Company Management
```
GET    /api/companies               # Get companies
POST   /api/companies               # Create company
GET    /api/companies/{id}          # Get company details
PUT    /api/companies/{id}          # Update company
```

## 🚀 Deployment

### Docker Deployment
```bash
# Build and run with Docker Compose
docker-compose up -d

# Or build individually
docker build -t job-platform-backend ./server
docker build -t job-platform-frontend ./client
```

### Production Deployment
1. **Backend**: Deploy Spring Boot as JAR on cloud platforms (AWS, GCP, Azure)
2. **Frontend**: Deploy Next.js on Vercel, Netlify, or similar platforms
3. **Database**: Use managed PostgreSQL services (AWS RDS, Google Cloud SQL)

### Environment-specific Configurations
- **Development**: Local PostgreSQL, file-based storage
- **Staging**: Cloud database, cloud storage
- **Production**: High-availability setup, CDN, load balancers

## 🤝 Contributing

We welcome contributions! Please follow these steps:

1. **Fork the repository**
2. **Create a feature branch**
   ```bash
   git checkout -b feature/amazing-feature
   ```
3. **Commit your changes**
   ```bash
   git commit -m 'Add amazing feature'
   ```
4. **Push to the branch**
   ```bash
   git push origin feature/amazing-feature
   ```
5. **Open a Pull Request**

### Development Guidelines
- Follow existing code style and conventions
- Write comprehensive tests for new features
- Update documentation for API changes
- Ensure all tests pass before submitting PR

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📞 Support

For support and questions:
- **Email**: support@jobplatform.com
- **Issues**: [GitHub Issues](https://github.com/your-username/job-platform/issues)
- **Documentation**: [Wiki](https://github.com/your-username/job-platform/wiki)

---

**Built with ❤️ using Spring Boot & Next.js**