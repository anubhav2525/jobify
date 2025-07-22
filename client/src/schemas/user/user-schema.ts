import {z} from "zod";

export const UserLoginSchema = z.object({
    email: z.string().email("Invalid email"),
    password: z.string().min(8, "Password required"),
});

export const UserRegistrationSchema = z.object({
    email: z.string().email("Invalid email address"),
    password: z.string().min(6, "Password must be at least 6 characters"),
    confirmPassword: z.string().min(6, "Confirm password is required"),
    firstName: z.string().min(1, "First name is required"),
    middleName: z.string(),
    lastName: z.string().min(1, "Last name is required"),
    phone: z.string(),
    country: z.string(),
    countryCode: z.string(),
    role: z.string(),
});

export const ChangePasswordSchema = z
    .object({
        email: z.string(),
        oldPassword: z.string().min(8, "Old password is required"),
        newPassword: z.string().min(8, "New password must be 8+ chars"),
        confirmPassword: z.string().min(8, "Confirm new password"),
    })
    .refine((data) => data.newPassword === data.confirmPassword, {
        path: ["confirmPassword"],
        message: "Passwords do not match",
    });

export const UserForgetPassword = z.object({
    email: z.string().email("Invalid email"),
});

export const UserVerifyEmail = z.object({
    email: z.string().email("Invalid email"),
    token: z.string().min(8, "Password required"),
});

export const UserResetPassword = z.object({
    email: z.string().email("Invalid email address"),
    token: z.string().min(8, "Old password is required"),
    newPassword: z.string().min(8, "New password must be 8+ chars"),
    confirmPassword: z.string().min(8, "Confirm new password"),
}).refine((data) => data.newPassword === data.confirmPassword, {
    path: ["confirmPassword"],
    message: "Passwords do not match",
});

export type ChangePasswordSchemaType = z.infer<typeof ChangePasswordSchema>;

export type UserRegistrationSchemaType = z.infer<typeof UserRegistrationSchema>;

export type UserLoginSchemaType = z.infer<typeof UserLoginSchema>;

export type UserForgetPasswordType = z.infer<typeof UserForgetPassword>;

export type UserResetPasswordType = z.infer<typeof UserResetPassword>;

export type UserVerifyEmailType = z.infer<typeof UserVerifyEmail>;
