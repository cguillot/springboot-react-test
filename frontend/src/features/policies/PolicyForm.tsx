"use client";

import {zodResolver} from "@hookform/resolvers/zod";
import {type ControllerRenderProps, useForm} from "react-hook-form";
import {z} from "zod/v4";
import {cn} from "@/lib/utils.ts";
import {format, formatISO} from "date-fns";

import {Button} from "@/components/ui/button.tsx";
import {Input} from "@/components/ui/input.tsx";

import {toast} from "sonner"

import {Form, FormControl, FormField, FormItem, FormLabel, FormMessage,} from "@/components/ui/form.tsx";
import {Select, SelectContent, SelectItem, SelectTrigger, SelectValue,} from "@/components/ui/select.tsx";
import {Popover, PopoverContent, PopoverTrigger} from "@/components/ui/popover.tsx";
import {CalendarIcon} from "lucide-react";
import {Calendar} from "@/components/ui/calendar.tsx";
import {
    createPolicy,
    type InsurancePolicy,
    type PolicyCreateCommand,
    type PolicyUpdateCommand,
    updatePolicy
} from "@/api/policies.tsx";

const pushNewPolicy = (newInsurancePolicy: z.infer<typeof formSchema>) => {
    const createPolicyCommand: PolicyCreateCommand = {
        data: {
            name: newInsurancePolicy.name,
            status: newInsurancePolicy.status,
            startDate: formatISO(newInsurancePolicy.startDate, {representation: 'date'}),
            endDate: formatISO(newInsurancePolicy.endDate, {representation: 'date'})
        }
    }

    return createPolicy(createPolicyCommand);
}

const pushUpdatedPolicy = (policy: InsurancePolicy, updatedInsurancePolicy: z.infer<typeof formSchema>) => {
    const updatePolicyCommand: PolicyUpdateCommand = {
        data: {
            name: updatedInsurancePolicy.name,
            status: updatedInsurancePolicy.status,
            startDate: formatISO(updatedInsurancePolicy.startDate, {representation: 'date'}),
            endDate: formatISO(updatedInsurancePolicy.endDate, {representation: 'date'})
        }
    }

    return updatePolicy(policy.id, updatePolicyCommand);
}

const formSchema = z.object({
    name: z
        .string()
        .min(2, {
            error: "Name must be at least 2 characters.",
        })
        .max(100, {
            error: "Name must be at most 100 characters.",
        }),
    status: z.enum(["active", "inactive"]),
    startDate: z.date({error: "Start date is required."}),
    endDate: z.date({error: "End date is required."}),
}).refine((data) => data.endDate > data.startDate, {
    message: "End date cannot be earlier than start date.",
    path: ["endDate"],
});

function CalendarFormFieldItem(name: string, field: ControllerRenderProps<any, string>) {
    return (
        <FormItem className="flex flex-col">
            <FormLabel>{name}</FormLabel>
            <Popover>
                <PopoverTrigger asChild>
                    <FormControl>
                        <Button
                            variant="outline"
                            className={cn(
                                "w-[240px] pl-3 text-left font-normal",
                                !field.value && "text-muted-foreground"
                            )}
                        >
                            {field.value ? (
                                format(field.value, "PPP")
                            ) : (
                                <span>Pick a date</span>
                            )}
                            <CalendarIcon className="ml-auto h-4 w-4 opacity-50"/>
                        </Button>
                    </FormControl>
                </PopoverTrigger>
                <PopoverContent className="w-auto p-0" align="start">
                    <Calendar
                        {...field}
                        mode="single"
                        selected={field.value}
                        onSelect={field.onChange}
                        disabled={(date) => date < new Date("2000-01-01")}
                        captionLayout="dropdown"
                        startMonth={new Date(new Date().getFullYear() - 2, 0)}
                        endMonth={new Date(new Date().getFullYear() + 10, 0)}
                        classNames={{
                            // Added to fix issue with dark mode
                            dropdown: cn(
                                "absolute dark:bg-card inset-0 opacity-0",
                            )
                        }}
                    />
                </PopoverContent>
            </Popover>
            <FormMessage/>
        </FormItem>
    )
}

interface PolicyFormProps {
    onSave: () => void,
    onClose: (refresh: boolean) => void,
    policy?: InsurancePolicy,
}

export function PolicyForm({onSave, onClose, policy = undefined}: PolicyFormProps) {
    const form = useForm<z.infer<typeof formSchema>>({
        resolver: zodResolver(formSchema),
        defaultValues: {
            name: policy?.name ?? "",
            status: policy?.status ?? "inactive",
            startDate: policy?.startDate ?? undefined,
            endDate: policy?.endDate ?? undefined,
        },
    });

    function onSubmit(values: z.infer<typeof formSchema>) {
        if (policy?.id != null) {
            pushUpdatedPolicy(policy, values).then(response => {
                console.log("✅ Submitted values:", values);
                onSave();
                toast("Policy successfully updated", {
                    description: (
                        <pre className="mt-2 w-[320px] rounded-md p-4">
                            {response.data?.name} (ID#{response.data?.id})
                        </pre>
                    ),
                })
            });
        } else {
            pushNewPolicy(values).then(response => {
                console.log("✅ Submitted values:", values);
                onSave();
                toast("Policy successfully created", {
                    description: (
                        <pre className="mt-2 w-[320px] rounded-md p-4">
                            {response.data?.name} (ID#{response.data?.id})
                        </pre>
                    ),
                })
            });
        }
    }

    return (
        <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-6">
                {policy && (
                    <FormItem>
                        <FormLabel>ID</FormLabel>
                        <div className="text-sm text-muted-foreground">
                            {policy.id}
                        </div>
                    </FormItem>
                )}
                {/* Name */}
                <FormField
                    control={form.control}
                    name="name"
                    render={({field}) => (
                        <FormItem>
                            <FormLabel>Policy Name</FormLabel>
                            <FormControl>
                                <Input placeholder="e.g. Insurance Policy ACME Corp. 2025" {...field} />
                            </FormControl>
                            <FormMessage/>
                        </FormItem>
                    )}
                />

                {/* Status */}
                <FormField
                    control={form.control}
                    name="status"
                    render={({field}) => (
                        <FormItem>
                            <FormLabel>Status</FormLabel>
                            <FormControl>
                                <Select onValueChange={field.onChange} value={field.value}>
                                    <SelectTrigger>
                                        <SelectValue placeholder="Select status"/>
                                    </SelectTrigger>
                                    <SelectContent>
                                        <SelectItem value="active">Active</SelectItem>
                                        <SelectItem value="inactive">Inactive</SelectItem>
                                    </SelectContent>
                                </Select>
                            </FormControl>
                            <FormMessage/>
                        </FormItem>
                    )}
                />

                {/* Start Date */}
                <FormField
                    control={form.control}
                    name="startDate"
                    render={({field}) => CalendarFormFieldItem("Start Date", field)}
                />

                {/* End Date */}
                <FormField
                    control={form.control}
                    name="endDate"
                    render={({field}) => CalendarFormFieldItem("End Date", field)}
                />

                {/* CreatedAd, UpdatedAt */}
                {policy && (
                    <FormItem>
                        <FormLabel>Created At</FormLabel>
                        <div className="text-sm text-muted-foreground">
                            {format(policy.createdAt, "PPP - HH:mm:ss XXXXX")}
                        </div>
                    </FormItem>
                )}
                {policy && (
                    <FormItem>
                        <FormLabel>Updated At</FormLabel>
                        <div className="text-sm text-muted-foreground">
                            {format(policy.updatedAt, "PPP - HH:mm:ss XXXXX")}
                        </div>
                    </FormItem>
                )}

                {/* Submit */}
                <div className={"flex justify-end gap-2"}>
                    <Button type="button" onClick={() => onClose(false)}>Cancel</Button>
                    <Button type="submit">{policy ? "Update" : "Create"}</Button>
                </div>

            </form>
        </Form>
    );
}
