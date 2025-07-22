"use client";
import React, { useState, useRef } from "react";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Textarea } from "@/components/ui/textarea";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { Checkbox } from "@/components/ui/checkbox";
import { Badge } from "@/components/ui/badge";
import { Switch } from "@/components/ui/switch";
import { Calendar } from "@/components/ui/calendar";
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover";
import {
  User,
  Briefcase,
  GraduationCap,
  Award,
  FileText,
  Plus,
  Trash2,
  Calendar as CalendarIcon,
  Upload,
  Edit,
  Save,
  X,
  Camera,
} from "lucide-react";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";

const ExperienceTab = () => {
  const [experiences, setExperiences] = useState([
    {
      companyName: "Tech Solutions Inc",
      jobTitle: "Senior Software Engineer",
      expStart: "2022-01-01",
      expEnd: null,
      isCurrentJob: true,
      description:
        "Leading frontend development team and architecting scalable web applications",
      achievements:
        "Increased application performance by 40%, mentored 3 junior developers",
    },
    {
      companyName: "StartupXYZ",
      jobTitle: "Frontend Developer",
      expStart: "2020-06-01",
      expEnd: "2021-12-31",
      isCurrentJob: false,
      description:
        "Developed responsive web applications using React and modern JavaScript",
      achievements:
        "Built 5 major features that increased user engagement by 25%",
    },
  ]);
  return <div>ExperienceTab</div>;
};

export default ExperienceTab;
