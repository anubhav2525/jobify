import AppSidebar from "@/components/sidebar/app-sidebar";
import React from "react";
import { SidebarInset, SidebarProvider } from "@/components/ui/sidebar";
import SiteHeader from "@/components/sidebar/site-header";

const ProtectedLayout = ({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) => {
  return (
    <SidebarProvider>
      <AppSidebar variant="inset" />
      <SidebarInset>
        <SiteHeader />
        <div className="flex flex-1 flex-col">
          <div className="flex-1 overflow-hidden">
            <main className="h-full w-full overflow-y-auto p-2 md:p-4">
              {children}
            </main>
          </div>
        </div>
      </SidebarInset>
    </SidebarProvider>
  );
};

export default ProtectedLayout;
