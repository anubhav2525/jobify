import React from "react";

const AuthenticationLayout = ({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) => {
  return <main className="min-h-screen w-full gradient-bg">{children}</main>;
};

export default AuthenticationLayout;
