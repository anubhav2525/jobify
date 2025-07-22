"use client";
import React, { useState, useEffect } from "react";
import {
  Sidebar,
  SidebarContent,
  SidebarFooter,
  SidebarGroup,
  SidebarGroupContent,
  SidebarGroupLabel,
  SidebarHeader,
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem,
} from "@/components/ui/sidebar";
import { GalleryVerticalEnd, LifeBuoy, Send } from "lucide-react";
import { NavSecondary } from "./nav-secondary";
import { NavUser } from "./nav-user";

// Types
interface MenuItem {
  title: string;
  url: string;
  permissions?: string[];
}

interface SidebarGroup {
  title: string;
  items: MenuItem[];
}

// Complete menu configuration with role-based permissions
const SIDEBAR_MENU_CONFIG: SidebarGroup[] = [
  {
    title: "GENERAL",
    items: [
      {
        title: "Dashboard",
        url: "/dashboard",
        permissions: ["ADMIN", "CANDIDATE", "RECRUITER", "COMPANY"],
      },
    ],
  },
  {
    title: "CONTENT",
    items: [
      // Admin only
      {
        title: "Companies",
        url: "/companies",
        permissions: ["ADMIN"],
      },
      {
        title: "Users",
        url: "/admin/users",
        permissions: ["ADMIN"],
      },

      // Candidate specific
      {
        title: "Jobs",
        url: "/candidate/jobs",
        permissions: ["CANDIDATE"],
      },
      {
        title: "Applications",
        url: "/candidate/applied-jobs",
        permissions: ["CANDIDATE"],
      },
      {
        title: "Saved Jobs",
        url: "/candidate/saved-jobs",
        permissions: ["CANDIDATE"],
      },
      {
        title: "Recommendations",
        url: "/candidate/recommendations",
        permissions: ["CANDIDATE"],
      },

      // Recruiter specific
      {
        title: "Candidates",
        url: "/recruiter/candidates",
        permissions: ["RECRUITER"],
      },
      {
        title: "Jobs",
        url: "/recruiter/jobs",
        permissions: ["RECRUITER", "ADMIN"],
      },
      {
        title: "Company Profile",
        url: "/recruiter/company-profile",
        permissions: ["RECRUITER"],
      },
      {
        title: "Applications",
        url: "/recruiter/applications",
        permissions: ["RECRUITER"],
      },

      // Company specific
      {
        title: "Candidates",
        url: "/company/candidates",
        permissions: ["COMPANY"],
      },
      {
        title: "Jobs",
        url: "/company/jobs",
        permissions: ["COMPANY"],
      },
      {
        title: "Employees",
        url: "/company/employees", // Fixed typo from "employeed"
        permissions: ["COMPANY"],
      },

      // Shared across multiple roles
      {
        title: "Interviews",
        url: "/interviews",
        permissions: ["CANDIDATE", "RECRUITER", "COMPANY"],
      },
      {
        title: "Profile",
        url: "/profile",
        permissions: ["ADMIN", "CANDIDATE", "RECRUITER", "COMPANY"],
      },
      {
        title: "Settings",
        url: "/settings",
        permissions: ["ADMIN", "CANDIDATE", "RECRUITER", "COMPANY"],
      },
    ],
  },
];

// Utility function to decode JWT and extract user role
const getUserRoleFromJWT = (): string | null => {
  try {
    const token =
      localStorage.getItem("token") || sessionStorage.getItem("token");
    if (!token) return null;

    // Decode JWT payload (this is a simple implementation)
    const payload = JSON.parse(atob(token.split(".")[1]));
    return payload.role || payload.userType || null;
  } catch (error) {
    console.error("Error decoding JWT:", error);
    return null;
  }
};

// Filter menu items based on user role
const getFilteredMenuItems = (userRole: string): SidebarGroup[] => {
  return SIDEBAR_MENU_CONFIG.map((group) => ({
    title: group.title,
    items: group.items.filter(
      (item) => !item.permissions || item.permissions.includes("CANDIDATE")
    ),
  })).filter((group) => group.items.length > 0); // Remove empty groups
};

// Custom hook for user role management
const useUserRole = () => {
  const [userRole, setUserRole] = useState<string | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const role = getUserRoleFromJWT();
    setUserRole(role);
    setLoading(false);
  }, []);

  return { userRole, loading };
};

const AppSidebar = ({ ...props }: React.ComponentProps<typeof Sidebar>) => {
  const { userRole, loading } = useUserRole();

  // Static data (could also come from JWT or API)
  const data = {
    user: {
      name: "shadcn",
      email: "m@example.com",
      avatar: "/avatars/shadcn.jpg",
    },
    navSecondary: [
      {
        title: "Support",
        url: "#",
        icon: LifeBuoy,
      },
      {
        title: "Feedback",
        url: "#",
        icon: Send,
      },
    ],
  };

  // Show loading state
  if (loading) {
    return (
      <Sidebar {...props}>
        <SidebarHeader>
          <div className="p-4">Loading...</div>
        </SidebarHeader>
      </Sidebar>
    );
  }

  // // Handle case where no role is found
  // if (!userRole) {
  //   return (
  //     <Sidebar {...props}>
  //       <SidebarHeader>
  //         <div className="p-4">Please login to continue</div>
  //       </SidebarHeader>
  //     </Sidebar>
  //   );
  // }

  // Get filtered menu items based on user role
  const filteredMenuItems = getFilteredMenuItems(
    userRole ? userRole : "CANDIDATE"
  );

  return (
    <Sidebar {...props}>
      <SidebarHeader>
        <SidebarMenu>
          <SidebarMenuItem>
            <SidebarMenuButton size="lg" asChild>
              <div className="flex items-center gap-2">
                <div className="flex aspect-square size-8 items-center justify-center rounded-lg bg-sidebar-primary text-sidebar-primary-foreground">
                  <GalleryVerticalEnd className="size-4" />
                </div>
                <div className="flex flex-col gap-0.5 leading-none">
                  <span className="font-semibold">Skill Bridge</span>
                  <span className="text-xs opacity-70">{userRole}</span>
                </div>
              </div>
            </SidebarMenuButton>
          </SidebarMenuItem>
        </SidebarMenu>
      </SidebarHeader>

      <SidebarContent>
        {filteredMenuItems.map((group) => (
          <SidebarGroup key={group.title}>
            <SidebarGroupLabel>{group.title}</SidebarGroupLabel>
            <SidebarGroupContent>
              <SidebarMenu>
                {group.items.map((item) => (
                  <SidebarMenuItem key={item.title}>
                    <SidebarMenuButton asChild>
                      <a href={item.url}>{item.title}</a>
                    </SidebarMenuButton>
                  </SidebarMenuItem>
                ))}
              </SidebarMenu>
            </SidebarGroupContent>
          </SidebarGroup>
        ))}
      </SidebarContent>

      <NavSecondary items={data.navSecondary} className="mt-auto" />
      <SidebarFooter>
        <NavUser user={data.user} />
      </SidebarFooter>
    </Sidebar>
  );
};

export default AppSidebar;
