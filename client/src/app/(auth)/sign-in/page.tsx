"use client";
import {zodResolver} from "@hookform/resolvers/zod";
import {useForm} from "react-hook-form";
import React from "react";
import {cn} from "@/lib/utils";
import {Button} from "@/components/ui/button";
import {Card, CardContent, CardDescription, CardHeader, CardTitle,} from "@/components/ui/card";
import {Input} from "@/components/ui/input";
import Link from "next/link";
import {Form, FormControl, FormField, FormItem, FormLabel, FormMessage,} from "@/components/ui/form";
import {UserLoginSchema, UserLoginSchemaType} from "@/schemas/user/user-schema";
import {userLogin} from "@/services/auth/AuthService";

const SignInPage = ({className}: React.ComponentProps<"div">) => {
    const form = useForm<UserLoginSchemaType>({
        resolver: zodResolver(UserLoginSchema),
        defaultValues: {
            email: "",
            password: ""
        },
    });

    const onSubmit = async (values: UserLoginSchemaType) => {
        await userLogin(values);
    }

    return (
        <div className={cn("flex flex-col gap-6", className)}>
            <Card className="bg-white/90 shadow-none  border-none">
                <CardHeader className="text-center">
                    <CardTitle className="text-xl">Welcome back</CardTitle>
                    <CardDescription>
                        <div className="text-center text-sm text-muted-foreground text">
                            Don&apos;t have an account?{" "}
                            <Link
                                href="/sign-up"
                                className="text-purple-500 hover:text-purple-600"
                            >
                                Sign up
                            </Link>
                        </div>
                    </CardDescription>
                </CardHeader>
                <CardContent>
                    <Form {...form}>
                        <form
                            onSubmit={form.handleSubmit(onSubmit)}
                            className="w-full grid gap-4"
                        >
                            <FormField
                                control={form.control}
                                name="email"
                                render={({field}) => (
                                    <FormItem>
                                        <FormLabel>Email address</FormLabel>
                                        <FormControl>
                                            <Input
                                                placeholder="John"
                                                className="bg-white rounded-sm"
                                                {...field}
                                            />
                                        </FormControl>
                                        <FormMessage/>
                                    </FormItem>
                                )}
                            />
                            <FormField
                                control={form.control}
                                name="password"
                                render={({field}) => (
                                    <FormItem>
                                        <div className="flex items-center justify-between">
                                            <FormLabel>Password</FormLabel>
                                            <Link
                                                className="text-sm text-purple-500 hover:text-purple-600"
                                                href={"/forget-password"}
                                            >
                                                Forget password?
                                            </Link>
                                        </div>
                                        <FormControl>
                                            <Input
                                                placeholder="Shan"
                                                className="bg-white rounded-sm"
                                                {...field}
                                            />
                                        </FormControl>
                                        <FormMessage/>
                                    </FormItem>
                                )}
                            />

                            <div className="w-full">
                                <div className="flex items-center justify-end gap-2">
                                    <Button
                                        type="submit"
                                        className="w-full bg-purple-500 hover:bg-purple-600"
                                    >
                                        Submit
                                    </Button>
                                </div>
                            </div>
                        </form>
                    </Form>
                </CardContent>
            </Card>
            <div
                className="text-white *:[a]:hover:text-primary text-center text-xs text-balance *:[a]:underline *:[a]:underline-offset-4">
                By clicking continue, you agree to our{" "}
                <Link href="/" className="hover:text-white">
                    Terms of Service
                </Link>{" "}
                and{" "}
                <Link href="/" className="hover:text-white">
                    Privacy Policy
                </Link>
                .
            </div>
        </div>
    );
};
export default SignInPage;
