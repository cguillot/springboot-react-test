import {type ColumnDef, flexRender, getCoreRowModel, type PaginationState, useReactTable} from "@tanstack/react-table";
import {Table, TableBody, TableCell, TableHead, TableHeader, TableRow} from "@/components/ui/table.tsx";
import {Skeleton} from "@/components/ui/skeleton.tsx";
import {Button} from "@/components/ui/button.tsx";
import {Pencil, Trash} from "lucide-react";
import Pagination from "@/components/Pagination.tsx";

export interface DataTableProps<TData, TValue> {
    columns: ColumnDef<TData, TValue>[]
    data: TData[]
    loading: boolean,
    onPaginationChange: any,
    pagination: PaginationState,
    rowCount: number,
    onDelete: any,
    onEdit: any,
    skeletonCount?: number;
}

export function PaginatedDataTable<TData, TValue>({
                                                      columns,
                                                      data,
                                                      loading,
                                                      onPaginationChange,
                                                      pagination,
                                                      rowCount,
                                                      onDelete,
                                                      onEdit,
                                                      skeletonCount = 10,
                                                  }: DataTableProps<TData, TValue>) {
    const table = useReactTable({
        data,
        columns,
        getCoreRowModel: getCoreRowModel(),
        manualPagination: true,
        onPaginationChange,
        state: {pagination},
        rowCount,
    })

    const skeletons = Array.from({length: skeletonCount}, (_, i) => i);
    const columnCount = table.getAllColumns().length;

    return (
        <div>
            <div className="rounded-md border">
                <Table>
                    {!loading && (
                        <TableHeader>
                            {table.getHeaderGroups().map((headerGroup) => (
                                <TableRow key={headerGroup.id}>
                                    {headerGroup.headers.map((header) => {
                                        return (
                                            <TableHead key={header.id}>
                                                {header.isPlaceholder
                                                    ? null
                                                    : flexRender(
                                                        header.column.columnDef.header,
                                                        header.getContext()
                                                    )}
                                            </TableHead>
                                        )
                                    })}
                                </TableRow>
                            ))}
                        </TableHeader>
                    )}
                    <TableBody>
                        {loading ? (
                            <>
                                {skeletons.map((skeleton) => (
                                    <TableRow key={skeleton}>
                                        {Array.from({length: columnCount}, (_, i) => i).map(
                                            (elm) => (
                                                <TableCell key={elm}>
                                                    <Skeleton className="h-4"/>
                                                </TableCell>
                                            )
                                        )}
                                    </TableRow>
                                ))}
                            </>
                        ) : (
                            <>
                                {table.getRowModel().rows?.length ? (
                                    table.getRowModel().rows.map((row) => (
                                        <TableRow key={row.id}
                                                  className="group hover:bg-muted relative transition-colors">
                                            {row.getVisibleCells().map((cell, index, array) => {
                                                const isLast = index === array.length - 1;

                                                return (
                                                    <TableCell key={cell.id} className="relative">
                                                        {flexRender(cell.column.columnDef.cell, cell.getContext())}
                                                        {isLast && (
                                                            <div className="absolute top-1/2 right-2 -translate-y-1/2 flex gap-2 bg-white/80 backdrop-blur-sm rounded
                                                                                                             px-2 py-1 opacity-0 group-hover:opacity-100 transition-opacity pointer-events-none">
                                                                <Button
                                                                    variant="secondary"
                                                                    size={"icon"}
                                                                    onClick={() => onEdit(row.original)}
                                                                    className="size-7 text-blue-600 hover:text-blue-800 pointer-events-auto">
                                                                    <Pencil className="w-2 h-2"/>
                                                                </Button>
                                                                <Button
                                                                    variant="secondary"
                                                                    size={"icon"}
                                                                    onClick={() => onDelete(row.getValue("id"))}
                                                                    className="size-7 text-red-600 hover:text-red-800 pointer-events-auto">
                                                                    <Trash className="w-2 h-2"/>
                                                                </Button>
                                                            </div>
                                                        )}
                                                    </TableCell>
                                                );
                                            })}
                                        </TableRow>
                                    ))
                                ) : (
                                    <TableRow>
                                        <TableCell colSpan={columns.length} className="h-24 text-center">
                                            No results.
                                        </TableCell>
                                    </TableRow>
                                )}
                            </>
                        )}
                    </TableBody>
                </Table>
                <hr/>
                <Pagination tableLib={table} sizes={[5, 10, 20]}/>
            </div>
        </div>
    )
}