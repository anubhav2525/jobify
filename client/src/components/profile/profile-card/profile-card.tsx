"use client";
import React, { useState, useRef } from "react";
import { Card, CardContent } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Save, X, Camera } from "lucide-react";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";

const ProfileCard = () => {
  const [profileImage, setProfileImage] = useState<string | null>(null);
  const [profileImagePreview, setProfileImagePreview] = useState<string | null>(
    null
  );
  const [isEditingImage, setIsEditingImage] = useState(false);
  const fileInputRef = useRef<HTMLInputElement>(null);

  // Sample user data
  const firstName = "John";
  const lastName = "Doe";
  const headline = "Software Engineer at Tech Solutions";
  const email = "johndeo@gmail.com";

  const handleImageUpload = (event: any) => {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (e) => {
        if (e.target && typeof e.target.result === "string") {
          setProfileImagePreview(e.target.result);
          setIsEditingImage(true);
        }
      };
      reader.readAsDataURL(file);
    }
  };

  const saveProfileImage = () => {
    setProfileImage(profileImagePreview);
    setIsEditingImage(false);
  };

  const cancelImageEdit = () => {
    setProfileImagePreview(null);
    setIsEditingImage(false);
    if (fileInputRef.current) {
      fileInputRef.current.value = "";
    }
  };

  return (
    <Card className="">
      <CardContent className="">
        <div className="flex items-center gap-6">
          <div className="relative">
            <Avatar className="h-32 w-32">
              <AvatarImage
                src={
                  profileImagePreview ||
                  profileImage ||
                  "/api/placeholder/150/150"
                }
                alt="Profile"
              />
              <AvatarFallback className="text-2xl">
                {firstName?.[0]}
                {lastName?.[0]}
              </AvatarFallback>
            </Avatar>
            <Button
              size="sm"
              variant="outline"
              className="absolute -bottom-2 -right-2 rounded-full h-8 w-8 p-0"
              onClick={() => fileInputRef.current?.click()}
            >
              <Camera className="h-4 w-4" />
            </Button>
          </div>
          <div className="flex-1">
            <h2 className="text-xl font-semibold">
              {firstName} {lastName}
            </h2>
            <p className="text-gray-600">{headline}</p>
            <p className="text-sm text-gray-500 mt-1">{email}</p>
            {isEditingImage && (
              <div className="flex gap-2 mt-4">
                <Button size="sm" onClick={saveProfileImage}>
                  <Save className="h-4 w-4 mr-2" />
                  Save Image
                </Button>
                <Button size="sm" variant="outline" onClick={cancelImageEdit}>
                  <X className="h-4 w-4 mr-2" />
                  Cancel
                </Button>
              </div>
            )}
          </div>
        </div>
        <input
          ref={fileInputRef}
          type="file"
          accept="image/*"
          onChange={handleImageUpload}
          className="hidden"
        />
      </CardContent>
    </Card>
  );
};

export default ProfileCard;
