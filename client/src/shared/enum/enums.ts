import { ComboBoxProps } from "@/components/custom-command/custom-command";

export enum CurrencyType {
  USD = "USD", // US Dollar
  EUR = "EUR", // Euro
  INR = "INR", // Indian Rupee
  GBP = "GBP", // British Pound
  AUD = "AUD", // Australian Dollar
  CAD = "CAD", // Canadian Dollar
  JPY = "JPY", // Japanese Yen
  CNY = "CNY", // Chinese Yuan
  CHF = "CHF", // Swiss Franc
  SGD = "SGD", // Singapore Dollar
  NZD = "NZD", // New Zealand Dollar
  ZAR = "ZAR", // South African Rand
  AED = "AED", // UAE Dirham
  SAR = "SAR", // Saudi Riyal
  KRW = "KRW", // South Korean Won
  BRL = "BRL", // Brazilian Real
  RUB = "RUB", // Russian Ruble
  MXN = "MXN", // Mexican Peso
  SEK = "SEK", // Swedish Krona
  NOK = "NOK", // Norwegian Krone
}

export enum AccountStatus {
  PENDING = "PENDING",
  ACTIVE = "ACTIVE",
  SUSPENDED = "SUSPENDED",
  DEACTIVATED = "DEACTIVATED",
}

export enum ApplicationStatus {
  APPLIED = "APPLIED",
  SCREENING = "SCREENING",
  SHORTLISTED = "SHORTLISTED",
  INTERVIEW_SCHEDULED = "INTERVIEW_SCHEDULED",
  INTERVIEWED = "INTERVIEWED",
  OFFER_MADE = "OFFER_MADE",
  HIRED = "HIRED",
  REJECTED = "REJECTED",
  WITHDRAWN = "WITHDRAWN",
}

export enum CompanySize {
  _1_10 = "1-10",
  _11_50 = "11-50",
  _51_200 = "51-200",
  _201_500 = "201-500",
  _501_1000 = "501-1000",
  _1000_PLUS = "1000+",
}

export enum EducationDegreeType {
  BACHELORS = "BACHELORS",
  MASTERS = "MASTERS",
  DIPLOMA = "DIPLOMA",
  DOCTORATE = "DOCTORATE",
  ASSOCIATE = "ASSOCIATE",
  CERTIFICATION = "CERTIFICATION",
  POSTGRADUATE_DIPLOMA = "POSTGRADUATE_DIPLOMA",
  OTHER = "OTHER",
}

export enum ExperienceLevel {
  ENTRY_LEVEL = "ENTRY_LEVEL",
  MID_LEVEL = "MID_LEVEL",
  SENIOR_LEVEL = "SENIOR_LEVEL",
  EXECUTIVE = "EXECUTIVE",
  INTERN = "INTERN",
  FRESHER = "FRESHER",
}

export enum DocumentType {
  RESUME = "RESUME",
  COVER_LETTER = "COVER_LETTER",
  PORTFOLIO = "PORTFOLIO",
  CERTIFICATE = "CERTIFICATE",
  COMPANY_LOGO = "COMPANY_LOGO",
  VERIFICATION_DOC = "VERIFICATION_DOC",
  REGISTRATION_CERTIFICATE = "REGISTRATION_CERTIFICATE",
  TAX_CERTIFICATE = "TAX_CERTIFICATE",
  LICENSE = "LICENSE",
  OTHER = "OTHER",
}

export enum Gender {
  MALE = "MALE",
  FEMALE = "FEMALE  ",
  TRANSGENDER = "TRANSGENDER",
  NON_BINARY = "NON_BINARY",
  GENDERQUEER = "GENDERQUEER",
  AGENDER = "AGENDER",
  BIGENDER = "BIGENDER",
  GENDERFLUID = "GENDERFLUID",
  INTERSEX = "INTERSEX",
  DEMIBOY = "DEMIBOY",
  DEMIGIRL = "DEMIGIRL",
  TWO_SPIRIT = "TWO_SPIRIT",
  THIRD_GENDER = "THIRD_GENDER",
  PANGENDER = "PANGENDER",
  NEUTROIS = "NEUTROIS",
  ANDROGYNE = "ANDROGYNE",
  OTHER = "OTHER",
  PREFER_NOT_TO_SAY = "PREFER_NOT_TO_SAY",
}

export enum InterviewStatus {
  SCHEDULED = "SCHEDULED",
  COMPLETED = "COMPLETED",
  CANCELLED = "CANCELLED",
  RESCHEDULED = "RESCHEDULED",
  NO_SHOW = "NO_SHOW",
}

export enum InterviewType {
  PHONE = "PHONE",
  VIDEO = "VIDEO",
  IN_PERSON = "IN_PERSON",
  TECHNICAL = "TECHNICAL",
  BEHAVIORAL = "BEHAVIORAL",
}

export enum JobStatus {
  DRAFT = "DRAFT",
  PUBLISHED = "PUBLISHED",
  PAUSED = "PAUSED",
  CLOSED = "CLOSED",
  EXPIRED = "EXPIRED",
}

export enum JobType {
  FULL_TIME = "FULL_TIME",
  PART_TIME = "PART_TIME",
  CONTRACT = "CONTRACT",
  TEMPORARY = "TEMPORARY",
  INTERNSHIP = "INTERNSHIP",
}

export enum LanguageLevel {
  BASIC = "BASIC",
  CONVERSATIONAL = "CONVERSATIONAL",
  PROFESSIONAL = "PROFESSIONAL",
  NATIVE = "NATIVE",
}

export enum Nationality {
  INDIAN = "INDIAN",
  AMERICAN = "AMERICAN",
  CANADIAN = "CANADIAN",
  AUSTRALIAN = "AUSTRALIAN",
  BRITISH = "BRITISH",
  FRENCH = "FRENCH",
  GERMAN = "GERMAN",
  JAPANESE = "JAPANESE",
  CHINESE = "CHINESE",
  BRAZILIAN = "BRAZILIAN",
  RUSSIAN = "RUSSIAN",
  SOUTH_AFRICAN = "SOUTH_AFRICAN",
  MEXICAN = "MEXICAN",
  ITALIAN = "ITALIAN",
  SPANISH = "SPANISH",
  KOREAN = "KOREAN",
  INDONESIAN = "INDONESIAN",
  TURKISH = "TURKISH",
  SAUDI = "SAUDI",
  OTHER = "OTHER",
}

export enum NotificationType {
  APPLICATION_UPDATE = "APPLICATION_UPDATE",
  INTERVIEW_SCHEDULED = "INTERVIEW_SCHEDULED",
  NEW_JOB_MATCH = "NEW_JOB_MATCH",
  SYSTEM_ANNOUNCEMENT = "SYSTEM_ANNOUNCEMENT",
}

export enum PaymentStatus {
  PENDING = "PENDING",
  COMPLETED = "COMPLETED",
  FAILED = "FAILED",
  REFUNDED = "REFUNDED",
}

export enum SalaryType {
  HOURLY = "HOURLY",
  MONTHLY = "MONTHLY",
  YEARLY = "YEARLY",
}

export enum WorkLocationType {
  REMOTE = "REMOTE",
  ONSITE = "ONSITE",
  HYBRID = "HYBRID",
}

export enum VerificationStatus {
  PENDING = "PENDING",
  VERIFIED = "VERIFIED",
  REJECTED = "REJECTED",
  UNDER_REVIEW = "UNDER_REVIEW",
}

export enum UserRole {
  ADMIN = "ADMIN",
  COMPANY_ADMIN = "COMPANY_ADMIN",
  RECRUITER = "RECRUITER",
  CANDIDATE = "CANDIDATE",
}

export enum SubscriptionStatus {
  ACTIVE = "ACTIVE",
  EXPIRED = "EXPIRED",
  CANCELLED = "CANCELLED",
  PENDING = "PENDING",
}

export enum StudyMode {
  FULL_TIME = "FULL_TIME",
  PART_TIME = "PART_TIME",
  DISTANCE = "DISTANCE",
  ONLINE = "ONLINE",
}

export function formatEnumValue<T extends string>(value: T): string {
  return value
    .trim()
    .split("_")
    .map((word) => word.charAt(0) + word.slice(1).toLowerCase())
    .join(" ");
}

export function getEnumOptions<T extends Record<string, string>>(
  enumObj: T
): ComboBoxProps[] {
  const values = Object.values(enumObj);
  const formatted = values.map((item) => formatEnumValue(item));

  return [
    { value: "", label: "" }, // default empty option
    ...values.map((value, index) => ({
      value,
      label: formatted[index],
    })),
  ];
}
