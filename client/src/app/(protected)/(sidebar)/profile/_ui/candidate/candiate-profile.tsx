"use client";
import React, {useState} from "react";
import {Tabs, TabsContent, TabsList, TabsTrigger} from "@/components/ui/tabs";
import {Award, Briefcase, FileText, GraduationCap, User} from "lucide-react";
import PersonalTab from "@/components/profile/personal-tab/personal-tab";
import ProfessionalTab from "@/components/profile/professional-tab/professional-tab";
import ProfileCard from "@/components/profile/profile-card/profile-card";
import EducationTab from "@/components/profile/education-tab/page";
import DocumentTab from "@/components/profile/document-tab/page";
import CertificationTab from "@/components/profile/certification-tab/certification-tab";
import {AlertDialog} from "@/components/ui/alert-dialog"
import ExperienceTab from "@/components/profile/experience-tab/page";

const CandidateProfile = () => {
    const [activeTab, setActiveTab] = useState("personal");

    return (
        <AlertDialog>
            <div className="h-fit w-full">
                <div className="flex flex-col gap-3">
                    {/* Header */}
                    <div className="flex justify-between items-center">
                        <div>
                            <h1 className="text-lg font-bold text-gray-900 dark:text-white">
                                Profile Settings
                            </h1>
                            <p className="text-gray-600 mt-1 dark:text-slate-300">
                                Manage your profile information and preferences
                            </p>
                        </div>
                    </div>

                    {/* Profile Image Section */}
                    <ProfileCard/>

                    {/* Tab Navigation */}
                    <Tabs
                        value={activeTab}
                        onValueChange={setActiveTab}
                        className="w-full flex flex-col gap-3"
                    >
                        <TabsList className="grid w-full grid-cols-7">
                            <TabsTrigger value="personal" className="flex items-center gap-2">
                                <User className="h-4 w-4"/>
                                <span className="hidden lg:flex">Personal</span>
                            </TabsTrigger>
                            <TabsTrigger
                                value="professional"
                                className="flex items-center gap-2"
                            >
                                <Briefcase className="h-4 w-4"/>
                                <span className="hidden lg:flex">Professional</span>
                            </TabsTrigger>
                            <TabsTrigger value="experience" className="flex items-center gap-2">
                                <Briefcase className="h-4 w-4"/>
                                <span className="hidden lg:flex">Experience</span>
                            </TabsTrigger>
                            <TabsTrigger value="education" className="flex items-center gap-2">
                                <GraduationCap className="h-4 w-4"/>
                                <span className="hidden lg:flex">Education</span>
                            </TabsTrigger>
                            <TabsTrigger
                                value="certifications"
                                className="flex items-center gap-2"
                            >
                                <Award className="h-4 w-4"/>
                                <span className="hidden lg:flex">Certifications</span>
                            </TabsTrigger>
                            <TabsTrigger value="documents" className="flex items-center gap-2">
                                <FileText className="h-4 w-4"/>
                                <span className="hidden lg:flex">Documents</span>
                            </TabsTrigger>
                            <TabsTrigger value="others" className="flex items-center gap-2">
                                <FileText className="h-4 w-4"/>
                                <span className="hidden lg:flex">Others</span>
                            </TabsTrigger>
                        </TabsList>

                        {/* Personal Information Tab */}
                        <TabsContent value="personal">
                            <PersonalTab/>
                        </TabsContent>

                        {/* Professional Information Tab */}
                        <TabsContent value="professional">
                            <ProfessionalTab/>
                        </TabsContent>

                        {/* Experience Tab */}
                        <TabsContent value="experience">
                            <ExperienceTab/>
                        </TabsContent>

                        {/* Education Tab */}
                        <TabsContent value="education">
                            <EducationTab/>
                        </TabsContent>

                        {/* Certifications Tab */}
                        <TabsContent value="certifications">
                            <CertificationTab/>
                        </TabsContent>

                        {/* Documents Tab */}
                        <TabsContent value="documents">
                            <DocumentTab/>
                        </TabsContent>
                    </Tabs>
                </div>
            </div>
        </AlertDialog>
    );
};

export default CandidateProfile;
