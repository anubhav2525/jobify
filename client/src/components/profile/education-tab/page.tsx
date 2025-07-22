"use client";

import React, {useEffect, useState} from "react";
import {zodResolver} from "@hookform/resolvers/zod";
import {useFieldArray, useForm} from "react-hook-form";
import {format} from "date-fns";
import {Card, CardContent, CardDescription, CardHeader, CardTitle,} from "@/components/ui/card";
import {Button} from "@/components/ui/button";
import {Input} from "@/components/ui/input";
import {Label} from "@/components/ui/label";
import {Textarea} from "@/components/ui/textarea";
import {Calendar} from "@/components/ui/calendar";
import {Switch} from "@/components/ui/switch";
import {Form, FormControl, FormField, FormItem, FormLabel, FormMessage,} from "@/components/ui/form";
import {Popover, PopoverContent, PopoverTrigger,} from "@/components/ui/popover";
import {Calendar as CalendarIcon, Edit, GraduationCap, Plus, Save, Trash2, X} from "lucide-react";
import {cn} from "@/lib/utils";
import {EducationDegreeType, getEnumOptions, StudyMode} from "@/shared/enum/enums";
import CustomCommand from "@/components/custom-command/custom-command";
import {EducationFormData, EducationFormValues, educationSchema} from "@/schemas/profile/profile-schema";

// Enum options
const degreeOptions = getEnumOptions(EducationDegreeType)
const studyModeOptions = getEnumOptions(StudyMode)

const EducationTab: React.FC = () => {
    const [editMode, setEditMode] = useState(false);
    const [loading, setLoading] = useState(true);
    const [saving, setSaving] = useState(false);
    const [initialData, setInitialData] = useState<EducationFormValues | null>(null);

    const form = useForm<EducationFormValues>({
        resolver: zodResolver(educationSchema),
        defaultValues: {
            education: [],
        },
        mode: "onChange"
    });

    const {fields, append, remove} = useFieldArray({
        control: form.control,
        name: "education",
    });

    // Load data on component mount
    useEffect(() => {
        const fetchEducationData = async () => {
            try {
                setLoading(true);
                // const response = await axios.get("/api/education");
                // const data = response.data || { education: [] };

                // Transform data to include currentlyStudying flag
                // const transformedData = {
                //     education: data.education.map((edu: any) => ({
                //         ...edu,
                //         currentlyStudying: !edu.educationEnd,
                //     })),
                // };

                // form.reset(transformedData);
                // setInitialData(transformedData);
            } catch (error) {
                console.error("Error fetching education data:", error);
                const emptyData = {education: []};
                form.reset(emptyData);
                setInitialData(emptyData);
            } finally {
                setLoading(false);
            }
        };

        fetchEducationData();
    }, [form]);

    // Handle form submission
    const onSubmit = async (data: EducationFormValues) => {
        try {
            setSaving(true);

            // Transform data before sending to API
            const apiData = {
                education: data.education.map((edu) => ({
                    ...edu,
                    educationEnd: edu.currentlyStuding ? "" : edu.educationEnd,
                })),
            };

            // await axios.put("/api/education", apiData);
            console.log(apiData)


            setInitialData(data);
            setEditMode(false);
        } catch (error) {
            console.error("Error saving education data:", error);
        } finally {
            setSaving(false);
        }
    };

    // Handle adding new education entry
    const handleAddEducation = () => {
        const newEducation: EducationFormData = {
            institutionName: "",
            degreeType: "",
            studyMode: "",
            fieldOfStudy: "",
            gradeGpa: "",
            educationStart: "",
            educationEnd: "",
            description: "",
            activities: "",
            orderIndex: fields.length,
            currentlyStuding: false,
        };

        append(newEducation);
        setEditMode(true);
    };

    // Reset form to initial data
    const handleDiscard = () => {
        if (initialData) {
            form.reset(initialData);
        }
        setEditMode(false);
    };

    // Enter edit mode
    const handleEdit = () => {
        setEditMode(true);
    };

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
                        <CardTitle className="flex items-center gap-2">
                            <GraduationCap className="h-5 w-5"/>
                            Education
                        </CardTitle>
                        <CardDescription>
                            Your educational background and qualifications
                        </CardDescription>
                    </div>
                    <div className="flex gap-2">
                        {editMode && fields.length > 0 && (
                            <Button
                                variant="outline"
                                size="sm"
                                onClick={handleAddEducation}
                                disabled={saving}
                            >
                                <Plus className="h-4 w-4 mr-2"/>
                                Add Education
                            </Button>
                        )}

                        {!editMode && fields.length > 0 && (
                            <Button
                                variant="outline"
                                onClick={handleEdit}
                                disabled={saving}
                            >
                                <Edit className="h-4 w-4 mr-2"/>
                                Edit
                            </Button>
                        )}

                        {/* {editMode && (
              <Button
                variant="outline"
                onClick={handleDiscard}
                disabled={saving}
              >
                <X className="h-4 w-4 mr-2" />
                Cancel
              </Button>
            )} */}
                    </div>
                </div>
            </CardHeader>
            <CardContent>
                {fields.length === 0 ? (
                    <div className="text-center py-8 space-y-4">
                        <GraduationCap className="h-12 w-12 mx-auto mb-4 opacity-50"/>
                        <p className="text-gray-500 mb-4">
                            No education entries added yet.
                        </p>
                        <Button
                            variant="outline"
                            onClick={handleAddEducation}
                            disabled={saving}
                        >
                            <Plus className="h-4 w-4 mr-2"/>
                            Add Your First Education
                        </Button>
                    </div>
                ) : (
                    <Form {...form}>
                        <form onSubmit={form.handleSubmit(onSubmit as any)} className="space-y-6">
                            {/* <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-6"> */}
                            {fields.map((field, index) => (
                                <Card key={field.id} className="relative">
                                    <CardContent className="p-6">
                                        {editMode && (
                                            <Button
                                                type="button"
                                                variant="ghost"
                                                size="sm"
                                                className="absolute top-4 right-4 text-red-500 hover:text-red-700 hover:bg-red-50 z-10"
                                                onClick={() => remove(index)}
                                            >
                                                <Trash2 className="h-4 w-4"/>
                                            </Button>
                                        )}

                                        {!editMode ? (
                                            // Display Mode
                                            <div className="space-y-4">
                                                <div className="flex justify-between items-start">
                                                    <div>
                                                        <h3 className="text-lg font-semibold">
                                                            {field.institutionName || "Institution Name"}
                                                        </h3>
                                                        <p className="text-blue-600 font-medium">
                                                            {degreeOptions.find(
                                                                (d) => d.value === field.degreeType
                                                            )?.label || field.degreeType}
                                                            {field.fieldOfStudy && ` in ${field.fieldOfStudy}`}
                                                        </p>
                                                    </div>
                                                    <div className="text-right text-sm text-gray-500">
                                                        <p>
                                                            {studyModeOptions.find(
                                                                (s) => s.value === field.studyMode
                                                            )?.label || field.studyMode}
                                                        </p>
                                                        <p>
                                                            {field.educationStart &&
                                                                format(new Date(field.educationStart), "MMM yyyy")}
                                                            {field.educationStart && field.educationEnd && " - "}
                                                            {field.educationEnd
                                                                ? format(new Date(field.educationEnd), "MMM yyyy")
                                                                : "Present"}
                                                        </p>
                                                    </div>
                                                </div>

                                                {field.gradeGpa && (
                                                    <div>
                                                        <Label className="text-sm font-medium text-gray-500">
                                                            Grade/GPA
                                                        </Label>
                                                        <p>{field.gradeGpa}</p>
                                                    </div>
                                                )}

                                                {field.description && (
                                                    <div>
                                                        <Label className="text-sm font-medium text-gray-500">
                                                            Description
                                                        </Label>
                                                        <p className="text-gray-700">{field.description}</p>
                                                    </div>
                                                )}

                                                {field.activities && (
                                                    <div>
                                                        <Label className="text-sm font-medium text-gray-500">
                                                            Activities
                                                        </Label>
                                                        <p className="text-gray-700">{field.activities}</p>
                                                    </div>
                                                )}
                                            </div>
                                        ) : (
                                            // Edit Mode
                                            <div className="space-y-4">
                                                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                                                    <FormField
                                                        control={form.control}
                                                        name={`education.${index}.institutionName`}
                                                        render={({field}) => (
                                                            <FormItem>
                                                                <FormLabel>Institution Name</FormLabel>
                                                                <FormControl>
                                                                    <Input
                                                                        placeholder="e.g., Harvard University"
                                                                        {...field}
                                                                    />
                                                                </FormControl>
                                                                <FormMessage/>
                                                            </FormItem>
                                                        )}
                                                    />

                                                    <FormField
                                                        control={form.control}
                                                        name={`education.${index}.degreeType`}
                                                        render={({field}) => (
                                                            <FormItem className="flex flex-col">
                                                                <FormLabel>Degree Type</FormLabel>
                                                                <CustomCommand
                                                                    value={field.value}
                                                                    onValueChange={field.onChange}
                                                                    options={degreeOptions}
                                                                    placeholder="Select degree"
                                                                    searchPlaceholder="Search degree"
                                                                />
                                                                <FormMessage/>
                                                            </FormItem>
                                                        )}
                                                    />
                                                </div>

                                                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                                                    <FormField
                                                        control={form.control}
                                                        name={`education.${index}.fieldOfStudy`}
                                                        render={({field}) => (
                                                            <FormItem>
                                                                <FormLabel>Field of Study</FormLabel>
                                                                <FormControl>
                                                                    <Input
                                                                        placeholder="e.g., Computer Science"
                                                                        {...field}
                                                                    />
                                                                </FormControl>
                                                                <FormMessage/>
                                                            </FormItem>
                                                        )}
                                                    />

                                                    <FormField
                                                        control={form.control}
                                                        name={`education.${index}.studyMode`}
                                                        render={({field}) => (
                                                            <FormItem className="flex flex-col">
                                                                <FormLabel>Study Mode *</FormLabel>
                                                                <CustomCommand
                                                                    value={field.value}
                                                                    onValueChange={field.onChange}
                                                                    options={studyModeOptions}
                                                                    placeholder="Select study mode"
                                                                    searchPlaceholder="Search study mode"
                                                                />
                                                                <FormMessage/>
                                                            </FormItem>
                                                        )}
                                                    />
                                                </div>

                                                <FormField
                                                    control={form.control}
                                                    name={`education.${index}.gradeGpa`}
                                                    render={({field}) => (
                                                        <FormItem>
                                                            <FormLabel>Grade/GPA</FormLabel>
                                                            <FormControl>
                                                                <Input
                                                                    placeholder="e.g., 3.8/4.0, First Class, A+"
                                                                    {...field}
                                                                />
                                                            </FormControl>
                                                            <FormMessage/>
                                                        </FormItem>
                                                    )}
                                                />

                                                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                                                    <FormField
                                                        control={form.control}
                                                        name={`education.${index}.educationStart`}
                                                        render={({field}) => (
                                                            <FormItem className="flex flex-col">
                                                                <FormLabel>Start Date</FormLabel>
                                                                <Popover>
                                                                    <PopoverTrigger asChild>
                                                                        <FormControl>
                                                                            <Button
                                                                                variant="outline"
                                                                                className={cn(
                                                                                    "w-full justify-start text-left font-normal",
                                                                                    !field.value && "text-muted-foreground"
                                                                                )}
                                                                            >
                                                                                <CalendarIcon className="mr-2 h-4 w-4"/>
                                                                                {field.value ? (
                                                                                    format(new Date(field.value), "PPP")
                                                                                ) : (
                                                                                    <span>Pick start date</span>
                                                                                )}
                                                                            </Button>
                                                                        </FormControl>
                                                                    </PopoverTrigger>
                                                                    <PopoverContent className="w-auto p-0">
                                                                        <Calendar
                                                                            mode="single"
                                                                            selected={field.value ? new Date(field.value) : undefined}
                                                                            onSelect={(date) => {
                                                                                if (date) {
                                                                                    field.onChange(format(date, "yyyy-MM-dd"));
                                                                                }
                                                                            }}
                                                                            disabled={(date) =>
                                                                                date > new Date() || date < new Date("1900-01-01")
                                                                            }
                                                                        />
                                                                    </PopoverContent>
                                                                </Popover>
                                                                <FormMessage/>
                                                            </FormItem>
                                                        )}
                                                    />

                                                    <FormField
                                                        control={form.control}
                                                        name={`education.${index}.educationEnd`}
                                                        render={({field}) => (
                                                            <FormItem className="flex flex-col">
                                                                <div className="flex items-center justify-between">
                                                                    <FormLabel>End Date</FormLabel>
                                                                    <FormField
                                                                        control={form.control}
                                                                        name={`education.${index}.currentlyStudying`}
                                                                        render={({field: switchField}) => (
                                                                            <FormItem
                                                                                className="flex flex-row items-center space-x-2 space-y-0">
                                                                                <FormControl>
                                                                                    <Switch
                                                                                        checked={switchField.value}
                                                                                        onCheckedChange={(checked) => {
                                                                                            switchField.onChange(checked);
                                                                                            if (checked) {
                                                                                                form.setValue(
                                                                                                    `education.${index}.educationEnd`,
                                                                                                    ""
                                                                                                );
                                                                                            }
                                                                                        }}
                                                                                    />
                                                                                </FormControl>
                                                                                <FormLabel
                                                                                    className="text-sm font-normal">
                                                                                    Currently studying
                                                                                </FormLabel>
                                                                            </FormItem>
                                                                        )}
                                                                    />
                                                                </div>
                                                                <Popover>
                                                                    <PopoverTrigger asChild>
                                                                        <FormControl>
                                                                            <Button
                                                                                variant="outline"
                                                                                className={cn(
                                                                                    "w-full justify-start text-left font-normal",
                                                                                    (!field.value || form.watch(`education.${index}.currentlyStudying`)) &&
                                                                                    "text-muted-foreground"
                                                                                )}
                                                                                disabled={form.watch(`education.${index}.currentlyStudying`)}
                                                                            >
                                                                                <CalendarIcon className="mr-2 h-4 w-4"/>
                                                                                {field.value && !form.watch(`education.${index}.currentlyStudying`) ? (
                                                                                    format(new Date(field.value), "PPP")
                                                                                ) : (
                                                                                    <span>Pick end date</span>
                                                                                )}
                                                                            </Button>
                                                                        </FormControl>
                                                                    </PopoverTrigger>
                                                                    <PopoverContent className="w-auto p-0">
                                                                        <Calendar
                                                                            mode="single"
                                                                            selected={field.value ? new Date(field.value) : undefined}
                                                                            onSelect={(date) => {
                                                                                if (date) {
                                                                                    field.onChange(format(date, "yyyy-MM-dd"));
                                                                                }
                                                                            }}
                                                                        />
                                                                    </PopoverContent>
                                                                </Popover>
                                                                <FormMessage/>
                                                            </FormItem>
                                                        )}
                                                    />
                                                </div>

                                                <FormField
                                                    control={form.control}
                                                    name={`education.${index}.description`}
                                                    render={({field}) => (
                                                        <FormItem>
                                                            <FormLabel>Description</FormLabel>
                                                            <FormControl>
                                                                <Textarea
                                                                    placeholder="Describe your coursework, projects, achievements..."
                                                                    className="min-h-[100px]"
                                                                    {...field}
                                                                />
                                                            </FormControl>
                                                            <FormMessage/>
                                                        </FormItem>
                                                    )}
                                                />

                                                <FormField
                                                    control={form.control}
                                                    name={`education.${index}.activities`}
                                                    render={({field}) => (
                                                        <FormItem>
                                                            <FormLabel>Activities & Societies</FormLabel>
                                                            <FormControl>
                                                                <Textarea
                                                                    placeholder="Student clubs, societies, sports teams..."
                                                                    className="min-h-[80px]"
                                                                    {...field}
                                                                />
                                                            </FormControl>
                                                            <FormMessage/>
                                                        </FormItem>
                                                    )}
                                                />
                                            </div>
                                        )}
                                    </CardContent>
                                </Card>
                            ))}

                            {editMode && (
                                <div className="flex justify-end gap-2 pt-4">
                                    <Button
                                        type="button"
                                        variant="outline"
                                        onClick={handleDiscard}
                                        disabled={saving}
                                    >
                                        <X className="h-4 w-4 mr-2"/>
                                        Discard Changes
                                    </Button>
                                    <Button type="submit" disabled={saving}>
                                        {saving ? (
                                            <div
                                                className="animate-spin rounded-full h-4 w-4 border-b-2 border-white mr-2"></div>
                                        ) : (
                                            <Save className="h-4 w-4 mr-2"/>
                                        )}
                                        Save Changes
                                    </Button>
                                </div>
                            )}
                        </form>
                    </Form>
                )}
            </CardContent>
        </Card>
    );
};

export default EducationTab;