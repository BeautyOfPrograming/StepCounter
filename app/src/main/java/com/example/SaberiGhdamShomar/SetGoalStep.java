package com.example.SaberiGhdamShomar;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class SetGoalStep extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.custome_dialog, null))
                // Add action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...

                        listener.onDialogOkClick(SetGoalStep.this);
                    }
                })
                .setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogCloseClick(SetGoalStep.this);
                    }
                });
        return builder.create();
    }


                    /* The activity that creates an instance of this dialog fragment must
                     * implement this interface in order to receive event callbacks.
                     * Each method passes the DialogFragment in case the host needs to query it. */
                    public interface StepDialogListener {
                        //        public void onDialogNumberPickerClick(DialogFragment dialog);
                        public void onDialogCloseClick(DialogFragment dialog);

                        public void onDialogOkClick(DialogFragment dialog);
                    }

                    // Use this instance of the interface to deliver action events
                    StepDialogListener listener;



                 // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
                        @Override
                        public void onAttach(Context context) {
                            super.onAttach(context);
                            // Verify that the host activity implements the callback interface
                            try {
                                // Instantiate the NoticeDialogListener so we can send events to the host
                                listener = (StepDialogListener) context;
                            } catch (ClassCastException e) {
                                // The activity doesn't implement the interface, throw exception
                                MainActivity mainActivity = new MainActivity();
                                throw new ClassCastException(mainActivity.toString()+ " must implement NoticeDialogListener");
                            }
                        }
}
