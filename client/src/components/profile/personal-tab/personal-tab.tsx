"use client";
import React, { useState, useEffect } from "react";
import axios, { formToJSON } from "axios";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover";
import {
  Command,
  CommandEmpty,
  CommandGroup,
  CommandInput,
  CommandItem,
  CommandList,
} from "@/components/ui/command";
import { Edit, Save, Check, ChevronsUpDown, Loader2 } from "lucide-react";
import { cn } from "@/lib/utils";
import {
  PersonalInformationSchema,
  PersonalInformationFormData,
} from "@/schemas/profile/profile-schema";
import { countryCodes, findCountryByCode } from "@/shared/enum/country-code";
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogTrigger
} from "@/components/ui/alert-dialog";

const PersonalTab = () => {
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [errors, setErrors] = useState<Record<string, string>>({});
  const [editMode, setEditMode] = useState(false);

  const toggleEditMode = () => {
    setEditMode((prev) => !prev);
  };

  const [formData, setFormData] = useState<PersonalInformationFormData>(
    {
      firstName: "",
      middleName: "",
      lastName: "",
      email: "",
      countryCode: "",
      phone: "",
      country: "",
    }
  );

  const [initialData, setInitialData] =
    useState<PersonalInformationFormData | null>(null); // for data backup  

  const form = useForm<PersonalInformationFormData>({
    resolver: zodResolver(PersonalInformationSchema),
    defaultValues: formData,
  });

  // Handle input changes
  const handleInputChange = (
    field: keyof PersonalInformationFormData,
    value: any
  ) => {
    setFormData((prev) => ({
      ...prev,
      [field]: value,
    }));

    // Clear error for this field
    if (errors[field]) {
      setErrors((prev) => ({
        ...prev,
        [field]: "",
      }));
    }
  };

  // Find the selected country based on the country code in the form
  const selectedCountry = findCountryByCode(form.watch("country"));

  // Load data on component mount
  useEffect(() => {
    loadPersonalInformation();
  }, []);

  // Update form when personalInfo changes
  useEffect(() => {
    form.reset(formData);
  }, [formData, form]);

  const loadPersonalInformation = async () => {
    try {
      setLoading(true);
      // Replace with your actual API endpoint
      // const response = await axios.get("/api/personal-information");
      // const data = response.data;

      // setPersonalInfo({
      //   id: data.id || "",
      //   firstName: data.firstName || "",
      //   middleName: data.middleName || "",
      //   lastName: data.lastName || "",
      //   email: data.email || "",
      //   countryCode: data.countryCode || "",
      //   phone: data.phone || "",
      //   country: data.country || "",
      // });
      // setInitialData(response.data); // store original

      // toast({
      //   title: "Success",
      //   description: "Personal information loaded successfully",
      // });
    } catch (error) {
      console.error("Error loading personal information:", error);
      // toast({
      //   title: "Error",
      //   description: "Failed to load personal information",
      //   variant: "destructive",
      // });
    } finally {
      setLoading(false);
    }
  };

  const onSubmit = async (data: PersonalInformationFormData) => {
    try {
      setSaving(true);

      // Replace with your actual API endpoint
      // const response = await axios.put("/api/personal-information", data);

      setFormData({
        firstName: "John",
        middleName: "Michael",
        lastName: "Doe",
        email: "john.doe@example.com",
        countryCode: "+1",
        phone: "+1234567890",
        country: "United States",
      });
      // setPersonalInfo(response.data);
      toggleEditMode();

      // toast({
      //   title: "Success",
      //   description: "Personal information updated successfully",
      // });
    } catch (error) {
      console.error("Error updating personal information:", error);
      // toast({
      //   title: "Error",
      //   description: "Failed to update personal information",
      //   variant: "destructive",
      // });
    } finally {
      setSaving(false);
    }
  };

  // const handleCountrySelect = (countryValue: string) => {
  //   const selectedCountry = searchCountries(countryValue);
  //   if (selectedCountry) {
  //     form.setValue(
  //       "country",
  //       findCountryByLabel(selectedCountry)?.label || ""
  //     );
  //     form.setValue("countryCode", selectedCountry.code);
  //   }
  //   setCountryOpen(false);
  // };

  if (loading) {
    return (
      <Card>
        <CardContent className="p-6">
          <div className="flex items-center justify-center h-32">
            <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-600"></div>
          </div>
        </CardContent>
      </Card>
    );
  }
  return (
    <>
      <Card>
        <CardHeader>
          <div className="flex justify-between items-center">
            <div>
              <CardTitle>Personal Information</CardTitle>
              <CardDescription>
                Your personal details and basic information
              </CardDescription>
            </div>
            {!editMode && <Button
              variant="ghost"
              size="sm"
              onClick={() => {
                // if (initialData) {
                //   setPersonalInfo(initialData); // Reset form
                // }
                toggleEditMode();
              }}
              disabled={saving}
            >
              <Edit className="h-4 w-4 mr-2" />
              Edit
            </Button>}
          </div>
        </CardHeader>
        <CardContent>
          {!editMode ? (
            // Display Mode
            <div className="space-y-6">
              <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                <div>
                  <Label className="text-sm font-medium text-gray-500">
                    First Name
                  </Label>
                  <p className="text-lg">{formData.firstName || "-"}</p>
                </div>
                <div>
                  <Label className="text-sm font-medium text-gray-500">
                    Middle Name
                  </Label>
                  <p className="text-lg">{formData.middleName || "-"}</p>
                </div>
                <div>
                  <Label className="text-sm font-medium text-gray-500">
                    Last Name
                  </Label>
                  <p className="text-lg">{formData.lastName || "-"}</p>
                </div>
              </div>
              <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                <div>
                  <Label className="text-sm font-medium text-gray-500">
                    Email
                  </Label>
                  <p className="text-lg">{formData.email || "-"}</p>
                </div>
                <div>
                  <Label className="text-sm font-medium text-gray-500">
                    Country
                  </Label>
                  <p className="text-lg">{formData.country || "-"}</p>
                </div>
                <div>
                  <Label className="text-sm font-medium text-gray-500">
                    Phone
                  </Label>
                  <p className="text-lg">
                    {formData.countryCode && formData.phone
                      ? `${formData.countryCode} ${formData.phone}`
                      : "-"}
                  </p>
                </div>
              </div>
            </div>
          ) : (
            // Edit Mode
            <Form {...form}>
              <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-6">
                {/* Name Fields */}
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                  <FormField
                    control={form.control}
                    name="firstName"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>First Name </FormLabel>
                        <FormControl>
                          <Input placeholder="Enter your first name" {...field} />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  <FormField
                    control={form.control}
                    name="middleName"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Middle Name</FormLabel>
                        <FormControl>
                          <Input
                            placeholder="Enter your middle name"
                            {...field}
                          />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  <FormField
                    control={form.control}
                    name="lastName"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Last Name </FormLabel>
                        <FormControl>
                          <Input placeholder="Enter your last name" {...field} />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                </div>

                {/* Contact Information */}
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                  <FormField
                    control={form.control}
                    name="email"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Email Address</FormLabel>
                        <FormControl>
                          <Input
                            type="email"
                            placeholder="Enter your email"
                            {...field}
                            disabled={true}
                          />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  <div>
                    <FormField
                      control={form.control}
                      name="country"
                      render={({ field }) => (
                        <FormItem className="flex flex-col">
                          <FormLabel>Country</FormLabel>
                          <Popover>
                            <PopoverTrigger asChild>
                              <FormControl className="">
                                <Button
                                  role="combobox"
                                  variant="secondary"
                                  className={cn(
                                    " justify-between border text-black dark:text-white ",
                                    !field.value &&
                                    "text-muted-foreground dark:text-white"
                                  )}
                                >
                                  {field.value ? (
                                    <span className="flex items-center gap-2 text-black dark:text-white">
                                      {/* <span className="font-mono text-sm">
                                      {field.value}
                                    </span> */}
                                      <span>
                                        {
                                          countryCodes.find(
                                            (c) => c.code === field.value
                                          )?.label
                                        }
                                      </span>
                                    </span>
                                  ) : (
                                    "Select country"
                                  )}
                                  <ChevronsUpDown className="opacity-50" />
                                </Button>
                              </FormControl>
                            </PopoverTrigger>
                            <PopoverContent className="w-[300px] p-0">
                              <Command>
                                <CommandInput
                                  placeholder="Search country..."
                                  className="h-9"
                                />
                                <CommandList>
                                  <CommandEmpty>No country found.</CommandEmpty>
                                  <CommandGroup>
                                    {countryCodes.map((country) => (
                                      <CommandItem
                                        value={`${country.label} ${country.code} ${country.dialingCode}`}
                                        key={country.code}
                                        onSelect={() => {
                                          form.setValue("country", country.label);
                                        }}
                                      >
                                        <div className="flex items-center justify-between w-full">
                                          <div className="flex items-center gap-2">
                                            <span>{country.label}</span>
                                          </div>
                                          <span className="text-muted-foreground text-sm">
                                            {country.dialingCode}
                                          </span>
                                        </div>
                                        <Check
                                          className={cn(
                                            "ml-auto",
                                            country.code === field.value
                                              ? "opacity-100"
                                              : "opacity-0"
                                          )}
                                        />
                                      </CommandItem>
                                    ))}
                                  </CommandGroup>
                                </CommandList>
                              </Command>
                            </PopoverContent>
                          </Popover>
                          <FormMessage />
                        </FormItem>
                      )}
                    />
                  </div>
                  <FormField
                    control={form.control}
                    name="phone"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Phone Number</FormLabel>
                        <FormControl>
                          <div className="flex">
                            <div className="flex items-center px-3 border border-r-0 rounded-l-md ">
                              <span className="text-sm font-mono">
                                {selectedCountry?.dialingCode || "+"}
                              </span>
                            </div>
                            <Input
                              placeholder="Enter phone number"
                              className="rounded-l-none  rounded-r-sm"
                              {...field}
                            />
                          </div>
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                </div>

                {/* Action Buttons */}
                <div className="flex justify-end gap-2">
                  <div className="flex justify-end gap-2">
                    <AlertDialog>
                      <AlertDialogTrigger asChild>
                        <Button type="button" variant="outline" disabled={saving}>
                          Cancel
                        </Button>
                      </AlertDialogTrigger>
                      <AlertDialogContent>
                        <AlertDialogHeader>
                          <AlertDialogTitle>Discard Changes?</AlertDialogTitle>
                          <AlertDialogDescription>
                            All unsaved changes will be lost. Do you want to continue?
                          </AlertDialogDescription>
                        </AlertDialogHeader>
                        <AlertDialogFooter>
                          <AlertDialogCancel>Go back</AlertDialogCancel>
                          <AlertDialogAction
                            onClick={() => {
                              if (initialData) {
                                setFormData(initialData);
                              }
                              toggleEditMode();
                            }}
                          >
                            Discard Changes
                          </AlertDialogAction>
                        </AlertDialogFooter>
                      </AlertDialogContent>
                    </AlertDialog>
                  </div>

                  <Button type="submit" disabled={saving}>
                    {saving ? (
                      <>
                        <Loader2 className="h-4 w-4 mr-2 animate-spin" />
                        Saving...
                      </>
                    ) : (
                      <>
                        <Save className="h-4 w-4 mr-2" />
                        Save Changes
                      </>
                    )}
                  </Button>
                </div>
              </form>
            </Form>
          )}
        </CardContent>
      </Card>
    </>
  );
};

export default PersonalTab;
