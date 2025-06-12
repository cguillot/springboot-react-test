import {deletePolicy, fetchPolicies, type InsurancePolicy, type PolicySearchResponse} from '@/api/policies.tsx';

import {type ColumnDef, type PaginationState,} from "@tanstack/react-table"
import {useEffect, useState} from "react";
import {Button} from "@/components/ui/button.tsx";
import {ShieldCheck} from 'lucide-react';
import {IconPlus} from "@tabler/icons-react";
import {CreatePolicyModal} from "@/features/policies/CreatePolicyModal.tsx";
import {EditPolicyModal} from "@/features/policies/EditPolicyModal.tsx";
import {PaginatedDataTable} from "@/components/PaginatedDataTable.tsx";
import {format} from "date-fns";

function formatDateTime(dateTimeValue: Date | string) {
    return format(dateTimeValue, "PPP - HH:mm:ss XXXXX");
}

function formatDate(dateValue: Date | string) {
    return format(dateValue, "PPP");
}


const columns: ColumnDef<InsurancePolicy>[] = [
    {
        accessorKey: "id",
        header: "ID",
    },
    {
        accessorKey: "name",
        header: "Name",
    },
    {
        accessorKey: "status",
        header: "Status",
    },
    {
        accessorKey: "startDate",
        header: "Coverage Start Date",
        cell: ({row}) => {
            return formatDate(row.getValue("startDate"));
        }
    },
    {
        accessorKey: "endDate",
        header: "Coverage End Date",
        cell: ({row}) => {
            return formatDate(row.getValue("endDate"));
        }
    },
    {
        accessorKey: "createdAt",
        header: "Created",
        cell: ({row}) => {
            const createAtString: string = row.getValue("createdAt");
            return formatDateTime(createAtString)
        },
    },
    {
        accessorKey: "updatedAt",
        header: "Last Updated",
        cell: ({row}) => {
            const updatedAtString: string = row.getValue("updatedAt");
            return formatDateTime(updatedAtString)
        },
    },
]

const fallbackData = {data: [], dataCount: 0, totalCount: 0}

function usePagination(initialSize = 10) {
    const [pagination, setPagination] = useState<PaginationState>({
        pageSize: initialSize,
        pageIndex: 0,
    });
    const {pageSize, pageIndex} = pagination;

    return {
        // table state
        onPaginationChange: setPagination,
        pagination,
        // API
        pageIndex: pageIndex,
        pageSize: pageSize,
    };
}

export default function PoliciesPage() {
    const [data, setData] = useState<PolicySearchResponse>(fallbackData);
    const [loading, setLoading] = useState(true);
    const [refresh, setRefresh] = useState(false)
    const [totalCount, setTotalCount] = useState(0)
    const [isCreatePolicyModalOpen, setIsCreatePolicyModalOpen] = useState(false);
    const [isEditPolicyModalOpen, setIsEditPolicyModalOpen] = useState(false);
    const [editedPolicy, setEditedPolicy] = useState<InsurancePolicy | undefined>(undefined);

    const {onPaginationChange, pagination, pageIndex, pageSize} = usePagination();

    useEffect(() => {
        setLoading(true);
        const load = async () => {
            const data: PolicySearchResponse = await fetchPolicies(pageIndex + 1, pageSize);
            console.log(`Fetched ${data.data.length} policies`)
            setData(data);
            setTotalCount(data.totalCount)
            setLoading(false);
            setRefresh(false)
        };
        load()
    }, [refresh, pageIndex, pageSize]);

    return (
        <div className="container mx-auto py-10">
            <div className="flex items-center justify-between mb-6">
                <div className="flex items-center gap-2">
                    <ShieldCheck className="w-5 h-5"/>
                    <h1 className="text-2xl font-bold tracking-tight">
                        {totalCount} {totalCount > 1 ? 'Policies' : 'Policy'}
                    </h1>
                </div>
                <div className="flex gap-2">
                    <Button
                        size="sm"
                        onClick={() => setIsCreatePolicyModalOpen(true)}
                    >
                        <IconPlus/>
                        New Policy...
                    </Button>
                </div>
            </div>
            <PaginatedDataTable
                columns={columns}
                data={data.data}
                loading={loading}
                onPaginationChange={onPaginationChange}
                pagination={pagination}
                rowCount={totalCount}
                onDelete={(id: string) => {
                    deletePolicy(id).then(() => setRefresh(true))
                }}
                onEdit={(editedPolicy: InsurancePolicy) => {
                    setEditedPolicy(editedPolicy)
                    setIsEditPolicyModalOpen(true)
                }}
            />
            <CreatePolicyModal
                isOpen={isCreatePolicyModalOpen}
                close={(refresh: boolean) => {
                    setIsCreatePolicyModalOpen(false);
                    if (refresh) {
                        setRefresh(true);
                    }
                }}
            />
            <EditPolicyModal
                isOpen={isEditPolicyModalOpen}
                close={(refresh: boolean) => {
                    setEditedPolicy(undefined);
                    setIsEditPolicyModalOpen(false);
                    if (refresh) {
                        setRefresh(true);
                    }
                }}
                policy={editedPolicy}
            />
        </div>
    )
}