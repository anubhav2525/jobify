import React from "react";
import CandidateProfile from "./_ui/candidate/candiate-profile";
import CompanyProfile from "./_ui/company/company-profile";
import RecruiterProfile from "./_ui/recruiter/recruiter-profile";
import AdminProfile from "./_ui/admin/admin-profile";

const ProfilePage = () => {
    const role = "Candidate"; // This should be dynamically set based on user roleandiidate

    if (role === "Candidate") return <CandidateProfile/>;
    else if (role === "RECRUITER") return <RecruiterProfile/>;
    else if (role === "ADMIN") return <AdminProfile/>;
    else if (role === "COMPANY_ADMIN") return <CompanyProfile/>;
    else return null;
};

export default ProfilePage;
