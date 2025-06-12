import {createRootRoute, Link, Outlet} from '@tanstack/react-router'
import {ThemeProvider} from "@/components/theme-provider.tsx";
import {ModeToggle} from "@/components/mode-toggle.tsx";
import {Toaster} from "@/components/ui/sonner.tsx";


export const Route = createRootRoute({
    component: () => (
        <ThemeProvider defaultTheme="dark" storageKey="vite-ui-theme">
            <div className="p-2 flex justify-between items-center">
                {/* Navigation links */}
                <div className="flex gap-2">
                    <Link to="/" className="[&.active]:font-bold">
                        Policies
                    </Link>
                </div>

                {/* Theme Toggle */}
                <div className="size-9">
                    <ModeToggle/>
                </div>
            </div>
            <hr/>
            <Outlet/>
            <Toaster/>
        </ThemeProvider>
    ),
})