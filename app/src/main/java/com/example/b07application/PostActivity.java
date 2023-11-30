package com.example.b07application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import postchecker.CSEntrepreneurStrategy;
import postchecker.CSInfoSysStrategy;
import postchecker.CSMajorOrSoftEngStrategy;
import postchecker.CSMinorStrategy;
import postchecker.CourseRequirement;
import postchecker.GenericProgramStrategy;
import postchecker.MathMajorStrategy;
import postchecker.MathSpecialistStrategy;
import postchecker.PostCheckResult;
import postchecker.StatsMajorStrategy;
import postchecker.StatsSpecialistMLOrDSStrategy;
import postchecker.StatsSpecialistSimpleStrategy;
import postchecker.UnlimitedStrategy;

public class PostActivity extends AppCompatActivity {
    final String csMinor = "Computer Science Minor";
    final String csMajor = "CS Major";
    final String csSoftEng = "CS Specialist: Software Engineering";
    final String csEntrepreneur = "CS Specialist: Entrepreneurship Stream";
    final String csInfoSys = "CS Specialist: Information Systems Stream";
    final String mathMajor = "Mathematics Major";
    final String mathSpec = "Mathematics Specialist";
    final String statsMajor = "Stats Major";
    final String statsMLOrDS = "Stats Specialist: Machine Learning or Data Specialist";
    final String statsSpecOther = "Stats Specialist: Other";
    final String statsMinor = "Stats Minor";

    int currentCourseIndex;
    GenericProgramStrategy requirementStrategy;

    void populateProgramSpinner(Spinner spinner) {
        ArrayList<String> spinnerItems = new ArrayList<String>();
        spinnerItems.add(csMinor);
        spinnerItems.add(csMajor);
        spinnerItems.add(csSoftEng);
        spinnerItems.add(csEntrepreneur);
        spinnerItems.add(csInfoSys);
        spinnerItems.add(mathMajor);
        spinnerItems.add(mathSpec);
        spinnerItems.add(statsMajor);
        spinnerItems.add(statsMLOrDS);
        spinnerItems.add(statsSpecOther);
        spinnerItems.add(statsMinor);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, spinnerItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    GenericProgramStrategy strToStrategy(String programName) {
        switch (programName) {
            case csMinor:
                return new CSMinorStrategy();

            case csMajor:
            case csSoftEng:
                return new CSMajorOrSoftEngStrategy();

            case csEntrepreneur:
                return new CSEntrepreneurStrategy();

            case csInfoSys:
                return new CSInfoSysStrategy();

            case mathMajor:
                return new MathMajorStrategy();

            case mathSpec:
                return new MathSpecialistStrategy();

            case statsMajor:
                return new StatsMajorStrategy();

            case statsMLOrDS:
                return new StatsSpecialistMLOrDSStrategy();

            case statsSpecOther:
                return new StatsSpecialistSimpleStrategy();

            case statsMinor:
                return new UnlimitedStrategy();
        }
        return null;
    }

    CourseRequirement currentCourse() {
        return requirementStrategy.requiredCourses.get(currentCourseIndex);
    }

    void prepareQuestion() {
        TextView instructionLabel = findViewById(R.id.label_instructions);
        TextView warningLabel = findViewById(R.id.label_warning);
        EditText gpaInput = findViewById(R.id.textbox_gpa);
        TextView statusLabel = findViewById(R.id.label_status);
        CourseRequirement course = currentCourse();

        if (currentCourse() == null) {
            PostCheckResult result = requirementStrategy.checkRequirements();
            statusLabel.setText(result.getMessage());
            if (result.getQualifies()) {
                instructionLabel.setText("Congratulations!");
            } else {
                instructionLabel.setText("Sorry!");
            }
            gpaInput.setVisibility(View.GONE);
            warningLabel.setVisibility(View.GONE);
            return;
        }
        gpaInput.setVisibility(View.VISIBLE);
        warningLabel.setVisibility(View.VISIBLE);

        String instruction = "Enter your GPA for " + course.toString();
        String warning = course.getWarning();
        String status = "Question " + (currentCourseIndex + 1)
                + " of " + requirementStrategy.requiredCourses.size();

        if (warning != null && warning.length() > 0) {
            warning += "\n";
        }
        warning += "If you have not taken this course, choose 0.";

        instructionLabel.setText(instruction);
        warningLabel.setText(warning);
        double storedGrade = currentCourse().getGrade();
        if (storedGrade > 0) {
            gpaInput.setText(Double.toString(storedGrade));
        } else {
            gpaInput.setText("");
        }
        statusLabel.setText(status);
    }

    double getGpaTextboxNum() {
        EditText gpaInput = findViewById(R.id.textbox_gpa);
        try {
            return Double.parseDouble(gpaInput.getText().toString());
        } catch (Exception ex) {
            return -1;
        }
    }

    void spinnerGPAToast() {
        Toast.makeText(this, "Please enter a GPA from 0.0 to 4.0",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_post);
        Button buttonPrev = findViewById(R.id.button_prev);
        Button buttonNext = findViewById(R.id.button_next);
        Spinner spinner = findViewById(R.id.spinner_program);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                requirementStrategy = strToStrategy(spinner.getSelectedItem().toString());
                if (requirementStrategy != null) {
                    currentCourseIndex = 0;
                    prepareQuestion();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        populateProgramSpinner(spinner);
        buttonNext.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentCourse() == null) {
                    finish();
                    return;
                }
                double givenGPA = getGpaTextboxNum();
                if (0 <= givenGPA && givenGPA <= 4) {
                    currentCourse().setGrade(givenGPA);
                    currentCourseIndex++;
                    prepareQuestion();
                    buttonPrev.setText("Previous");
                } else {
                    spinnerGPAToast();
                }
            }
        });

        buttonPrev.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentCourseIndex == 0) {
                    finish();
                    return;
                }
                currentCourseIndex--;
                if (currentCourseIndex == 0) {
                    buttonPrev.setText("Back");
                }
                prepareQuestion();
            }
        });
    }
}