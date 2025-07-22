"use client";
import React, {useEffect, useState} from "react";
import {z} from "zod";
import {useForm} from "react-hook-form";
import {Card, CardContent, CardDescription, CardHeader, CardTitle,} from "@/components/ui/card";
import {Button} from "@/components/ui/button";
import {Input} from "@/components/ui/input";
import {Label} from "@/components/ui/label";
import {Textarea} from "@/components/ui/textarea";
import {Calendar} from "@/components/ui/calendar";
import {Popover, PopoverContent, PopoverTrigger,} from "@/components/ui/popover";
import {Calendar as CalendarIcon, Edit, Loader2, Save} from "lucide-react";
import {cn} from "@/lib/utils";
import {format} from "date-fns";
import {Gender, getEnumOptions, Nationality} from "@/shared/enum/enums";
import {ProfessionalInformationFormData, ProfessionalInformationSchema,} from "@/schemas/profile/profile-schema";
import CustomCommand from "@/components/custom-command/custom-command";
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
import {Form, FormControl, FormField, FormItem, FormLabel, FormMessage,} from "@/components/ui/form";
import {zodResolver} from "@hookform/resolvers/zod";

const ProfessionalTab = () => {
    // enum values
    const gender = getEnumOptions(Gender);
    const nationality = getEnumOptions(Nationality);

    const [loading, setLoading] = useState(true);
    const [saving, setSaving] = useState(false);
    const [errors, setErrors] = useState<Record<string, string>>({});
    const [editMode, setEditMode] = useState(false);

    const toggleEditMode = () => {
        setEditMode((prev) => !prev);
    };

    const [datePickerOpen, setDatePickerOpen] = useState(false);

    const [formData, setFormData] = useState<ProfessionalInformationFormData>({
        headline: "",
        summary: "",
        currentJobTitle: "",
        currentCompany: "",
        totalExperienceYears: "0",
        dateOfBirth: new Date(),
        gender: "",
        nationality: "",
    });

    const [initialData, setInitialData] =
        useState<ProfessionalInformationFormData | null>(null); // data backup

    const form = useForm<ProfessionalInformationFormData>({
        resolver: zodResolver(ProfessionalInformationSchema),
        defaultValues: formData,
    });

    // Load data on component mount
    useEffect(() => {
        const fetchProfessionalInfo = async () => {
            try {
                setLoading(true);
                // const response = await axios.get("/api/professional-information");
                // setFormData(response.data);
                // setInitialData(response.data); // store original
            } catch (error) {
                console.error("Error fetching professional information:", error);
                // Handle error appropriately
            } finally {
                setLoading(false);
            }
        };

        fetchProfessionalInfo();
    }, []);

    // Handle form submission
    const handleSave = async () => {
        try {
            setSaving(true);
            setErrors({});

            // Validate form data
            const validatedData = ProfessionalInformationSchema.parse(formData);

            // Save to API
            // await axios.put("/api/professional-information", validatedData);

            // Show success message
        } catch (error) {
            if (error instanceof z.ZodError) {
                const fieldErrors: Record<string, string> = {};
                error.errors.forEach((err) => {
                    if (err.path) {
                        fieldErrors[err.path.join(".")] = err.message;
                    }
                });
                setErrors(fieldErrors);
            } else {
                console.error("Error saving professional information:", error);
                // Handle API error
            }
        } finally {
            setSaving(false);
        }
    };

    // Handle input changes
    const handleInputChange = (
        field: keyof ProfessionalInformationFormData,
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

    const onSubmit = (data: ProfessionalInformationFormData) => {
        console.log(data)
    }

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
        <Card>
            <CardHeader>
                <div className="flex justify-between items-center">
                    <div>
                        <CardTitle>Professional Information</CardTitle>
                        <CardDescription>
                            Your career details and personal information
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
                        <Edit className="h-4 w-4 mr-2"/>
                        Edit
                    </Button>}
                </div>
            </CardHeader>
            <CardContent>
                {!editMode ? (
                    // Display Mode
                    <div className="space-y-6">
                        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                            <div>
                                <Label className="text-sm font-medium text-gray-500">
                                    Professional Headline
                                </Label>
                                <p className="text-lg">
                                    {formData.headline || "Not specified"}
                                </p>
                            </div>
                            <div>
                                <Label className="text-sm font-medium text-gray-500">
                                    Current Job Title
                                </Label>
                                <p className="text-lg">
                                    {formData.currentJobTitle || "Not specified"}
                                </p>
                            </div>
                        </div>

                        <div>
                            <Label className="text-sm font-medium text-gray-500">
                                Professional Summary
                            </Label>
                            <p className="text-base leading-relaxed">
                                {formData.summary || "No summary provided"}
                            </p>
                        </div>

                        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                            <div>
                                <Label className="text-sm font-medium text-gray-500">
                                    Current Company
                                </Label>
                                <p className="text-lg">
                                    {formData.currentCompany || "Not specified"}
                                </p>
                            </div>
                            <div>
                                <Label className="text-sm font-medium text-gray-500">
                                    Total Experience
                                </Label>
                                <p className="text-lg">
                                    {formData.totalExperienceYears || 0} years
                                </p>
                            </div>
                        </div>

                        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                            <div>
                                <Label className="text-sm font-medium text-gray-500">
                                    Date of Birth
                                </Label>
                                <p className="text-lg">
                                    {formData.dateOfBirth
                                        ? format(new Date(formData.dateOfBirth), "PPP")
                                        : "Not specified"}
                                </p>
                            </div>
                            <div>
                                <Label className="text-sm font-medium text-gray-500">
                                    Gender
                                </Label>
                                <p className="text-lg">
                                    {gender.find((g) => g.value === formData.gender)?.label ||
                                        "Not specified"}
                                </p>
                            </div>
                            <div>
                                <Label className="text-sm font-medium text-gray-500">
                                    Nationality
                                </Label>
                                <p className="text-lg">
                                    {nationality.find((n) => n.value === formData.nationality)
                                        ?.label || "Not specified"}
                                </p>
                            </div>
                        </div>
                    </div>
                ) : (
                    // Edit Mode
                    <Form {...form}>
                        <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-6">
                            {/* Name Fields */}
                            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                                <div className="grid md:col-span-2">
                                    <FormField
                                        control={form.control}
                                        name="summary"
                                        render={({field}) => (
                                            <FormItem>
                                                <FormLabel>Summary</FormLabel>
                                                <FormControl>
                                                    <Textarea placeholder="Enter your summary" rows={3} {...field} />
                                                </FormControl>
                                                <FormMessage/>
                                            </FormItem>
                                        )}
                                    />
                                </div>
                                <FormField
                                    control={form.control}
                                    name="headline"
                                    render={({field}) => (
                                        <FormItem>
                                            <FormLabel>Headline</FormLabel>
                                            <FormControl>
                                                <Input placeholder="Enter your headline" {...field} />
                                            </FormControl>
                                            <FormMessage/>
                                        </FormItem>
                                    )}
                                />
                                <FormField
                                    control={form.control}
                                    name="currentJobTitle"
                                    render={({field}) => (
                                        <FormItem>
                                            <FormLabel>Current job title</FormLabel>
                                            <FormControl>
                                                <Input placeholder="Enter current job title" {...field} />
                                            </FormControl>
                                            <FormMessage/>
                                        </FormItem>
                                    )}
                                />
                                <FormField
                                    control={form.control}
                                    name="currentCompany"
                                    render={({field}) => (
                                        <FormItem>
                                            <FormLabel>Current company</FormLabel>
                                            <FormControl>
                                                <Input placeholder="Enter current company" {...field} />
                                            </FormControl>
                                            <FormMessage/>
                                        </FormItem>
                                    )}
                                />
                                <FormField
                                    control={form.control}
                                    name="totalExperienceYears"
                                    render={({field}) => (
                                        <FormItem>
                                            <FormLabel>Total years of experience</FormLabel>
                                            <FormControl>
                                                <Input placeholder="Enter your experience" {...field} />
                                            </FormControl>
                                            <FormMessage/>
                                        </FormItem>
                                    )}
                                />
                                <div className="grid md:col-span-2">
                                    <div className="w-full grid lg:grid-cols-3 gap-4">
                                        <FormField
                                            control={form.control}
                                            name="dateOfBirth"
                                            render={({field}) => (
                                                <FormItem className="flex flex-col w-full">
                                                    <FormLabel>Date of birth</FormLabel>
                                                    <Popover>
                                                        <PopoverTrigger asChild>
                                                            <FormControl>
                                                                <Button
                                                                    variant={"outline"}
                                                                    className={cn(
                                                                        "pl-3 text-left font-normal",
                                                                        !field.value && "text-muted-foreground"
                                                                    )}
                                                                >
                                                                    {field.value ? (
                                                                        format(field.value, "PPP")
                                                                    ) : (
                                                                        <span>Pick a date</span>
                                                                    )}
                                                                    <CalendarIcon
                                                                        className="ml-auto h-4 w-4 opacity-50"/>
                                                                </Button>
                                                            </FormControl>
                                                        </PopoverTrigger>
                                                        <PopoverContent className="w-auto p-0" align="start">
                                                            <Calendar
                                                                mode="single"
                                                                selected={field.value}
                                                                onSelect={field.onChange}
                                                                disabled={(date) =>
                                                                    date > new Date() || date < new Date("1900-01-01")
                                                                }
                                                                captionLayout="dropdown"
                                                            />
                                                        </PopoverContent>
                                                    </Popover>
                                                    <FormMessage/>
                                                </FormItem>
                                            )}
                                        />
                                        <FormField
                                            control={form.control}
                                            name="gender"
                                            render={({field}) => (
                                                <FormItem>
                                                    <FormLabel>Gender</FormLabel>
                                                    <FormControl>
                                                        <CustomCommand
                                                            value={field.value}
                                                            onValueChange={field.onChange}
                                                            options={gender}
                                                            placeholder="Select gender"
                                                            searchPlaceholder="Search gender..."
                                                        />
                                                    </FormControl>
                                                    <FormMessage/>
                                                </FormItem>
                                            )}
                                        />
                                        <FormField
                                            control={form.control}
                                            name="nationality"
                                            render={({field}) => (
                                                <FormItem>
                                                    <FormLabel>Nationality</FormLabel>
                                                    <FormControl>
                                                        <CustomCommand
                                                            value={field.value}
                                                            onValueChange={field.onChange}
                                                            options={nationality}
                                                            placeholder="Select nationality"
                                                            searchPlaceholder="Search nationality..."
                                                        />
                                                    </FormControl>
                                                    <FormMessage/>
                                                </FormItem>
                                            )}
                                        />
                                    </div>
                                </div>
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
                                            <Loader2 className="h-4 w-4 mr-2 animate-spin"/>
                                            Saving...
                                        </>
                                    ) : (
                                        <>
                                            <Save className="h-4 w-4 mr-2"/>
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
    );
};

export default ProfessionalTab;
