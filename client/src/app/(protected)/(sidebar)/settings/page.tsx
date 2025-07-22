import ChangePasswordUI from '@/components/setting/change-password/change-password'
import { AlertDialog } from '@/components/ui/alert-dialog'
import React from 'react'

const SettingsPage = () => {
    return (
        <AlertDialog>
            <div className="h-fit w-full">
                <div className="flex flex-col gap-3">
                    {/* Header */}
                    <div className="flex justify-between items-center">
                        <div>
                            <h1 className="text-lg font-bold text-gray-900 dark:text-white">
                                Settings
                            </h1>
                            <p className="text-gray-600 mt-1 dark:text-slate-300">
                                Manage your account settings
                            </p>
                        </div>
                    </div>

                    <ChangePasswordUI />
                </div>
            </div>
        </AlertDialog>
    )
}

export default SettingsPage