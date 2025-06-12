import {Footer} from "react-day-picker";
import {Button} from "@/components/ui/button.tsx";
import {Select, SelectContent, SelectItem, SelectTrigger, SelectValue} from "@/components/ui/select.tsx";

interface PaginationProps {
    tableLib?: any,
    sizes: number[],
    // any props that come into the component
}

export default function Pagination({tableLib, sizes}: PaginationProps) {
    return (
        <Footer className="flex items-center justify-center w-full gap-1 py-4">
            <Button
                size="sm"
                disabled={!tableLib.getCanPreviousPage()}
                onClick={() => tableLib.setPageIndex(0)}
            >
                {'<<'}
            </Button>
            <Button
                size="sm"
                disabled={!tableLib.getCanPreviousPage()}
                onClick={tableLib.previousPage}
            >
                {'<'}
            </Button>
            {tableLib.getPageCount() > 0 ? (
                <span className={"items-center justify-center"}>{`page ${
                    tableLib.getState().pagination.pageIndex + 1
                } of ${tableLib.getPageCount()}`}</span>
            ) : (
                <span className={"items-center justify-center"}>no data</span>
            )}

            <Button
                size="sm"
                disabled={!tableLib.getCanNextPage()}
                onClick={tableLib.nextPage}>
                {'>'}
            </Button>
            <Button
                size="sm"
                disabled={!tableLib.getCanNextPage()}
                onClick={() => tableLib.setPageIndex(tableLib.getPageCount() - 1)}
            >
                {'>>'}
            </Button>
            <span>Show: </span>
            <Select
                value={String(tableLib.getState().pagination.pageSize)}
                onValueChange={(e) => tableLib.setPageSize(parseInt(e, 10))}
            >
                <SelectTrigger>
                    <SelectValue placeholder="count"/>
                </SelectTrigger>
                <SelectContent>
                    {sizes.map((size: number) => (
                        <SelectItem key={String(size)} value={String(size)}>{size}</SelectItem>
                    ))}
                </SelectContent>
            </Select>
            <span> items per page</span>
        </Footer>
    )
}