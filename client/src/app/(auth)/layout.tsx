import AuthIntroSection from "@/components/AuthUI/intro-section/intro-section";
import { GalleryVerticalEnd } from "lucide-react";
import React from "react";
import Link from "next/link";
import { Toaster } from "@/components/ui/sonner";
const AuthenticationLayout = ({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) => {
  return (
    <>
      <main className="min-h-screen w-full gradient-bg grid grid-cols-1 lg:grid-cols-7">
        <div className="w-full h-full grid lg:col-span-4">
          <div className="w-full h-ful flex flex-col items-center justify-center">
            <Link
              href="/"
              className="flex items-center gap-2 self-center font-medium mb-4 text-white hover:text-[#9810fa] transition-colors duration-200"
            >
              <div className=" size-6 items-center justify-center rounded-md">
                <GalleryVerticalEnd className="size-4" />
              </div>
              Acme Inc.
            </Link>
            {children}
          </div>
        </div>
        <div className="w-full h-full lg:col-span-3 hidden lg:grid">
          <AuthIntroSection />
        </div>
      </main>
      <Toaster position="top-center" />
    </>
  );
};

export default AuthenticationLayout;
