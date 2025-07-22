import NavbarUI from "@/components/navbar/navbar";
import React from "react";

const PublicLayoutWithNav = ({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) => {
  return (
    <main className="w-full min-h-full flex flex-col items-center justify-start bg-background">
      <NavbarUI />
      {children}
    </main>
  );
};

export default PublicLayoutWithNav;
