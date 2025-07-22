"use server"
import {
    UserForgetPasswordType,
    UserLoginSchemaType,
    UserRegistrationSchemaType,
    UserVerifyEmailType
} from "@/schemas/user/user-schema";
import {toast} from "sonner";
import axios from "axios";
import serverConfig from "@/lib/server-config";

export const userRegistration = async (values: UserRegistrationSchemaType) => {
    try {
        // TODO: check and save user data & handle the response
        const res = await axios.post(`${serverConfig.authApiEndpoint}/sign-up`, values);
        if (res.status === 201) {
            toast.success("User created successfully", {
                description: "Your account has been created successfully, Let's verify",
            })

            // extract values
            const data = res.data.data;
            // redirect("/verify-email",data)
        } else if (res.status === 208) {
            toast.info("User already exist", {
                description: "Your account has not been created",
            })
        }

        console.log(values)
    } catch (error: unknown) {
        if (axios.isAxiosError(error)) {
            const message = error.response?.data?.message || "Account creation failed.";
            toast.error(message);
        } else {
            toast.error("An unexpected error occurred.");
        }
    }
}

export const userVerifyEmail = async (values: UserVerifyEmailType) => {
    try {
        // TODO: check and verify the user email
        console.log(values)
    } catch (error: unknown) {
        console.log(error);
    }
}
export const userLogin = async (values: UserLoginSchemaType) => {
    try {
        // TODO: check and verify user data & handle the response
        console.log(values)
    } catch (error: unknown) {
        console.log(error);
    }
}

export const signOut = async () => {
    try {
        // TODO: Implement the logout functionality
    } catch (error: unknown) {
        console.log(error);
    }
}

export const userForgetPassword = async (values: UserForgetPasswordType) => {
    try {
        // TODO: Implement the forget password if valid email exists
        console.log(values)
    } catch (error: unknown) {
        console.log(error);
    }
}

export const userResetPassword = async (values: UserRegistrationSchemaType) => {
    try {
        // TODO: Update the password after token received by user
        console.log(values)
    } catch (error: unknown) {
        console.log(error);
    }
}

export const changePassword = async (values: UserForgetPasswordType) => {
    try {
        // TODO: change the password if user logged
        console.log(values)
    } catch (error: unknown) {
        console.log(error);
    }
}