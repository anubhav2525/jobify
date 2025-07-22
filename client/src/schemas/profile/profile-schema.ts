import {
  DocumentType,
} from "@/shared/enum/enums";
import { z } from "zod";

export const PersonalInformationSchema = z.object({
  id: z.string().optional(),
  email: z.string().email("Invalid email address").optional(),
  phone: z.string(),
  countryCode: z.string(),
  country: z.string(),
  firstName: z.string(),
  lastName: z.string(),
  middleName: z.string(),
});

export const ProfileCardSchema = z.object({
  id: z.string(),
  firstName: z.string(),
  lastName: z.string(),
  headline: z.string(),
  email: z.string(),
});

export const ProfileImageSchema = z.object({
  id: z.string().optional(),
  profileImageUrl: z.string().url("Invalid image URL").optional(),
  profileImageFile: z.instanceof(File).optional(),
});

// Basic candidate information schema
export const ProfessionalInformationSchema = z.object({
  headline: z
    .string()
    .min(1, "Headline is required")
    .max(255, "Headline must be less than 255 characters"),
  summary: z.string().optional(),
  currentJobTitle: z.string().optional(),
  currentCompany: z.string().optional(),
  totalExperienceYears: z.string(),
  dateOfBirth: z.date({
    required_error: "A date of birth is required.",
  }),
  gender: z.string(),
  nationality: z.string(),
});

// Salary and preferences schema
export const salaryPreferencesSchema = z.object({
  currentSalary: z
    .number()
    .min(0, "Current salary cannot be negative")
    .max(999999999999, "Current salary is too high")
    .optional(),
  expectedSalary: z
    .number()
    .min(0, "Expected salary cannot be negative")
    .max(999999999999, "Expected salary is too high")
    .optional(),
  currencyType: z.string(),
  noticePeriodDays: z
    .number()
    .min(0, "Notice period cannot be negative")
    .max(365, "Notice period cannot exceed 365 days")
    .optional(),
  preferredLocations: z.array(z.string()).default([]),
  willingToRelocate: z.boolean().default(false),
  isOpenToWork: z.boolean().default(true),
  isProfilePublic: z.boolean().default(true),
});

// Skills schema
export const skillsSchema = z.object({
  skills: z
    .array(
      z
        .string()
        .min(1, "Skill name cannot be empty")
        .max(30, "Skill name too long")
    )
    .min(1, "At least one skill is required")
    .max(50, "Maximum 50 skills allowed"),
});

// Social links schema
export const socialLinksSchema = z.object({
  linkedIn: z.string().url("Invalid LinkedIn URL").optional().or(z.literal("")),
  github: z.string().url("Invalid GitHub URL").optional().or(z.literal("")),
  portfolio: z
    .string()
    .url("Invalid Portfolio URL")
    .optional()
    .or(z.literal("")),
  twitter: z.string().url("Invalid Twitter URL").optional().or(z.literal("")),
  website: z.string().url("Invalid Website URL").optional().or(z.literal("")),
});

// Education schema
export const educationSchema = z
  .object({
    institutionName: z
      .string()
      .min(1, "Institution name is required")
      .max(255, "Institution name too long"),
    degreeType: z.string(),
    studyMode: z.string(),
    fieldOfStudy: z
      .string()
      .max(255, "Field of study name too long"),
    gradeGpa: z.string().max(20, "Grade/GPA too long"),
    educationStart: z.string(),
    educationEnd: z.string(),
    description: z.string(),
    currentlyStuding: z.boolean(),
    activities: z.string(),
    orderIndex: z.number(),
  })
  .refine(
    (data) => {
      if (data.educationStart && data.educationEnd) {
        return new Date(data.educationStart) <= new Date(data.educationEnd);
      }
      return true;
    },
    {
      message: "Start date must be before end date",
      path: ["educationEnd"],
    }
  );

// Experience schema
export const experienceSchema = z
  .object({
    companyName: z
      .string()
      .min(1, "Company name is required")
      .max(255, "Company name too long"),
    jobTitle: z
      .string()
      .min(1, "Job title is required")
      .max(255, "Job title too long"),
    expStart: z.string().optional(),
    expEnd: z.string().optional(),
    isCurrentJob: z.boolean().default(false),
    description: z.string().optional(),
    achievements: z.string().optional(),
  })
  .refine(
    (data) => {
      if (data.expStart && data.expEnd && !data.isCurrentJob) {
        return new Date(data.expStart) <= new Date(data.expEnd);
      }
      return true;
    },
    {
      message: "Start date must be before end date",
      path: ["expEnd"],
    }
  );

// Certification schema
export const certificationSchema = z
  .object({
    certificationName: z
      .string()
      .min(1, "Certification name is required")
      .max(255, "Certification name too long"),
    issuingOrganization: z
      .string()
      .min(1, "Issuing organization is required")
      .max(255, "Issuing organization name too long"),
    issueDate: z.string().optional(),
    expiryDate: z.string().optional(),
    credentialId: z.string().max(255, "Credential ID too long").optional(),
    credentialUrl: z
      .string()
      .url("Invalid credential URL")
      .max(500, "Credential URL too long")
      .optional()
      .or(z.literal("")),
    verificationStatus: z.string(),
  })
  .refine(
    (data) => {
      if (data.issueDate && data.expiryDate) {
        return new Date(data.issueDate) <= new Date(data.expiryDate);
      }
      return true;
    },
    {
      message: "Issue date must be before expiry date",
      path: ["expiryDate"],
    }
  );

// Document schema
export const documentSchema = z.object({
  documentType: z.nativeEnum(DocumentType),
  fileName: z
    .string()
    .min(1, "File name is required")
    .max(255, "File name too long"),
  fileUrl: z.string().url("Invalid file URL").max(500, "File URL too long"),
  fileSize: z
    .number()
    .min(1, "File size must be positive")
    .max(10485760, "File size cannot exceed 10MB") // 10MB limit
    .optional(),
  isPrimary: z.boolean().default(false),
});

// Combined schemas for multi-step forms
// export const candidatePersonalInfoSchema =
//   basicInfoSchema.merge(socialLinksSchema);
export const candidatePreferencesSchema =
  salaryPreferencesSchema.merge(skillsSchema);

// Array schemas for lists
export const educationListSchema = z.object({
  education: z.array(educationSchema).optional().default([]),
});

export const experienceListSchema = z.object({
  experiences: z.array(experienceSchema).optional().default([]),
});

export const certificationListSchema = z.object({
  certifications: z.array(certificationSchema).optional().default([]),
});

export const documentListSchema = z.object({
  documents: z.array(documentSchema).optional().default([]),
});

// Complete candidate profile schema
// export const completeCandidateSchema = basicInfoSchema
//   .merge(salaryPreferencesSchema)
//   .merge(skillsSchema)
//   .merge(socialLinksSchema)
//   .merge(educationListSchema)
//   .merge(experienceListSchema)
//   .merge(certificationListSchema)
//   .merge(documentListSchema);

// Type exports for TypeScript
// export type BasicInfoFormData = z.infer<typeof basicInfoSchema>;
export type SalaryPreferencesFormData = z.infer<typeof salaryPreferencesSchema>;
export type SkillsFormData = z.infer<typeof skillsSchema>;
export type SocialLinksFormData = z.infer<typeof socialLinksSchema>;
export type EducationFormData = z.infer<typeof educationSchema>;
export type EducationFormValues = z.infer<typeof educationListSchema>;
export type ExperienceFormData = z.infer<typeof experienceSchema>;
export type CertificationFormData = z.infer<typeof certificationSchema>;
export type DocumentFormData = z.infer<typeof documentSchema>;
export type PersonalInformationFormData = z.infer<
  typeof PersonalInformationSchema
>;
export type ProfileCardFormData = z.infer<typeof ProfileCardSchema>;
export type ProfileImageFormData = z.infer<typeof ProfileImageSchema>;
export type CandidatePreferencesFormData = z.infer<
  typeof candidatePreferencesSchema
>;
export type ProfessionalInformationFormData = z.infer<
  typeof ProfessionalInformationSchema
>;

// export type CompleteCandidateFormData = z.infer<typeof completeCandidateSchema>;

// Utility schemas for partial updates
// export const updateBasicInfoSchema = basicInfoSchema.partial();
export const updateSalaryPreferencesSchema = salaryPreferencesSchema.partial();
export const updateSkillsSchema = skillsSchema.partial();
export const updateSocialLinksSchema = socialLinksSchema.partial();
