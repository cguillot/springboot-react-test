import {Dialog, DialogContent, DialogHeader, DialogTitle,} from "@/components/ui/dialog.tsx"
import {PolicyForm} from "@/features/policies/PolicyForm.tsx";

type CreatePolicyModalProps = {
    isOpen: boolean;
    close: (refresh: boolean) => void;
};

export const CreatePolicyModal = ({
                                      isOpen,
                                      close,
                                  }: CreatePolicyModalProps) => {

    const handleCloseDialog = (updated: boolean | undefined) => {
        console.log("close")
        close(!!updated)
    }

    return (
        <Dialog open={isOpen} onOpenChange={close}>
            <DialogContent>
                <DialogHeader>
                    <DialogTitle>New Insurance policy</DialogTitle>
                </DialogHeader>
                <PolicyForm onSave={() => handleCloseDialog(true)} onClose={handleCloseDialog}/>
            </DialogContent>
        </Dialog>
    )
}