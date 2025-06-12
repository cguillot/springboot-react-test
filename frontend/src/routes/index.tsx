import {createFileRoute} from '@tanstack/react-router'
import PoliciesPage from "@/features/policies/PoliciesPage.tsx";

export const Route = createFileRoute('/')({
    component: Index,
})

function Index() {
    return (
        <div className="p-2">
            <PoliciesPage/>
        </div>
    )
}