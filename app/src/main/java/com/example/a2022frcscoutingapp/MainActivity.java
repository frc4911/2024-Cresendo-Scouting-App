package com.example.a2022frcscoutingapp;
//idk how to change the file name to 2023 frc
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.slider.Slider;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

//ignore all the commented out things :)
public class MainActivity extends AppCompatActivity {

    public static final String FIlE_NAME_KEY = "standard";

    private static RatingBar rDefenseBar;

    private String strDefenseRating;
    private static Switch sLeftCommunity;

    private View lConstraint;

    private EditText eScoutID;
    private EditText eTeamNumber;
    private EditText eMatchNumber;
    private static EditText eDefenseRating;

    private Button bMatchPhase;
    private static Button bTarmac;
    private Button bMinusTopCube;
    private Button bMinusTopCone;

    private Button bPlusTopCube;
    private Button bPlusTopCone;

    private Button bPlusMidCube;
    private Button bMinusMidCube;
    private Button bPlusMidCone;
    private Button bMinusMidCone;

    private Button bPlusBotCone;
    private Button bPlusBotCube;
    private Button bMinusBotCube;
    private Button bMinusBotCone;

    private Button bRobotProblem;

    private Button bPlusCollect;
    private Button bMinusCollect;

    private Button bFoul;

    private Button bCard;

    private static Button bType;

    private Button bSave;

    private TextView tUpperCube, tLowerCube, tUpperCone, tLowerCone, tMidCube, tMidCone, tCollect;

    private static boolean leftCommunity;
    
    private static int zMatchNumber, zScoutID, zAllianceColor,
            zTeamNumber,
            zMatchPhase, zTarmac,zAutoTarmac,
            zAutoTopCube, zAutoMidCube, zAutoBotCube, zAutoTopCone, zAutoMidCone, zAutoBotCone,
            zTopCube, zMidCube, zBotCube, zTopCone, zMidCone, zBotCone,
            zCollect,
            zType,

            zRobotProblem,

            zFoul, zCard,
            zSaveCount;

    private static boolean scoringInfoEditable = false;
    private static boolean autoInforEditable = false;
    private static boolean rotationIntoEditable = false;
    private static boolean endgameInfoEditable = false;
    private static boolean saveEnabled = false;
    private static boolean isAuto = false;


    private static final int darkThemeRed = Color.argb(151, 255, 138, 128);
    private static final int darkThemeGreen = Color.argb(151, 204, 255, 144);
    private static final int darkThemeBlue = Color.argb(171, 130, 177, 255);

    private static final int r2gGradient1 = Color.argb(151,255,184,131);
    private static final int r2gGradient2 = Color.argb(151,255,227,135);
    private static final int r2gGradient3 = Color.argb(151,241,255,140);


    private static final int backgroundBlack = Color.argb(200, 0, 0, 0);
    private static final int backgroundBlue = Color.argb(150, 37, 52, 77);

    private static final int secretPurple = Color.argb(254, 179, 136, 255); //?


    private String[] climbDisplayText = {"No Climb", "Touching", "Leveled", "In Community"};

    private Map<Integer, List<String>> matchMap = new HashMap<>();




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String defaultValue = sharedPref.getString(FIlE_NAME_KEY, "PLEASE SET FILENAME");
        setTitle(defaultValue);
        setContentView(R.layout.activity_main);

        lConstraint = findViewById(R.id.lConstraint);

        // = findViewById(R.id.eMatchNumber);
        eScoutID = findViewById(R.id.eScoutID);
        eTeamNumber = findViewById(R.id.eTeamNumber);
        eMatchNumber = findViewById(R.id.eMatchNumber);
        eDefenseRating = findViewById(R.id.eDefenseRating);
        rDefenseBar = findViewById(R.id.rDefenseBar);
        //bAllianceColor = findViewById(R.id.bAllianceColor);
        bMatchPhase = findViewById(R.id.bMatchPhase);
        bTarmac = findViewById(R.id.bInitiationLine);
        bPlusTopCube = findViewById(R.id.bPlusTopCube);
        bMinusTopCube = findViewById(R.id.bMinusTopCube);
        bPlusBotCube = findViewById(R.id.bPlusBotCube);
        bMinusBotCube = findViewById(R.id.bMinusBotCube);
        bPlusTopCone = findViewById(R.id.bPlusTopCone);
        bMinusTopCone = findViewById(R.id.bMinusTopCone);
        bPlusBotCone = findViewById(R.id.bPlusBotCone);
        bMinusBotCone = findViewById(R.id.bMinusBotCone);
        bPlusMidCone = findViewById(R.id.bPlusMidCone);
        bMinusMidCone = findViewById(R.id.bMinusMidCone);
        bPlusMidCube = findViewById(R.id.bPlusMidCube);
        bMinusMidCube = findViewById(R.id.bMinusMidCube);
        bRobotProblem = findViewById(R.id.bRobotProblem);
        bType = findViewById(R.id.bType);
        bFoul = findViewById(R.id.bFoul);
        bPlusCollect = findViewById(R.id.bPlusCollect);
        bMinusCollect = findViewById(R.id.bMinusCollect);
        sLeftCommunity = findViewById(R.id.sLeftCommunity);
        //sDock = findViewById(R.id.sDock);
        //sEngage = findViewById(R.id.sEngage);

        //sLeftCommunity = findViewById(R.id.sLeftCommunity);
        //sTeleop = findViewById(R.id.sTeleop);

        bCard = findViewById(R.id.bCard);
        bSave = findViewById(R.id.bSave);

        tUpperCube = findViewById(R.id.tUpperCube);
        tLowerCube = findViewById(R.id.tLowerCube);
        tUpperCone = findViewById(R.id.tUpperCone);
        tLowerCone = findViewById(R.id.tLowerCone);
        tMidCube = findViewById(R.id.tMidCube);
        tMidCone = findViewById(R.id.tMidCone);
        tCollect = findViewById(R.id.tCollect);


        zeroAllData();

        setAllElementEditability();

        matchMap = parseMatchFile();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.Settings:
                showSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showSettings() {
        DialogFragment newFragment = new FileNameDialogFragment();
        newFragment.show(getSupportFragmentManager(), "cones/cubes");
    }

    private static void zeroAllData() {

        zTeamNumber = 0;
        zMatchPhase = 0;
        zTarmac = 0;
        zAutoTarmac = 0;
        zAutoTopCube = 0;
        zAutoTopCone = 0;
        zAutoMidCube = 0;
        zAutoMidCone = 0;
        zAutoBotCube = 0;
        zAutoBotCone = 0;
        zTopCone = 0;
        zMidCone = 0;
        zBotCone = 0;
        zTopCube = 0;
        zMidCube = 0;
        zBotCube = 0;
        zType = 0;
        zRobotProblem = 0;
        zFoul = 0;
        zCard = 0;
        zSaveCount = 0;
        zCollect = 0;
        leftCommunity = false;
        sLeftCommunity.setChecked(leftCommunity);
        bType.setText("Robot Type");
        bTarmac.setText("Climb: None");
        rDefenseBar.setRating(0);
        eDefenseRating.setText("Defense Rating");
    }

    private void setAllElementEditability() {

        setButtonEditable(bTarmac, scoringInfoEditable);

        setButtonEditable(bMinusTopCube, scoringInfoEditable);
        setButtonEditable(bPlusBotCube, scoringInfoEditable);
        setButtonEditable(bPlusTopCube, scoringInfoEditable);
        setButtonEditable(bMinusBotCube, scoringInfoEditable);

        setButtonEditable(bMinusTopCone, scoringInfoEditable);
        setButtonEditable(bMinusBotCone, scoringInfoEditable);
        setButtonEditable(bPlusTopCone, scoringInfoEditable);
        setButtonEditable(bPlusBotCone, scoringInfoEditable);

        setButtonEditable(bMinusMidCone, scoringInfoEditable);
        setButtonEditable(bMinusMidCube, scoringInfoEditable);
        setButtonEditable(bPlusMidCube, scoringInfoEditable);
        setButtonEditable(bPlusMidCone, scoringInfoEditable);
        setButtonEditable(bPlusCollect, scoringInfoEditable);
        setButtonEditable(bMinusCollect, scoringInfoEditable);



        setButtonEditable(bSave, saveEnabled);
    }

    private void setButtonEditable(Button b, Boolean enabled) {

        b.setEnabled(enabled);
        b.setTextColor(enabled ? Color.WHITE : Color.GRAY);
    }

    private void updateScoringPortText() {

        int upperDisplayCone, lowerDisplayCone, middleDisplayCone, upperDisplayCube, lowerDisplayCube, middleDisplayCube, collect;
        if(isAuto){
            upperDisplayCone = zAutoTopCone;
            middleDisplayCone = zAutoMidCone;
            lowerDisplayCone = zAutoBotCone;
            upperDisplayCube = zAutoTopCube;
            middleDisplayCube = zAutoMidCube;
            lowerDisplayCube = zAutoBotCube;
            collect = zCollect;

        } else {

            upperDisplayCone = zTopCone;
            middleDisplayCone = zMidCone;
            lowerDisplayCone = zBotCone;
            upperDisplayCube = zTopCube;
            middleDisplayCube = zMidCube;
            lowerDisplayCube = zBotCube;
            collect = zCollect;
            
        }
        tUpperCone.setText("Top = " + upperDisplayCone);
        tMidCone.setText("Mid = " + middleDisplayCone);
        tLowerCone.setText("Bot = " + lowerDisplayCone);
        tUpperCube.setText("Top = " + upperDisplayCube);
        tMidCube.setText("Mid = " + middleDisplayCube);
        tLowerCube.setText("Bot = " + lowerDisplayCube);
        tCollect.setText("Collect: " + collect);
    }

    private boolean isEmpty(EditText etText) { // a method to check if an edit text is empty

        return etText.getText().toString().trim().length() == 0;

    }


    public void clickMatchPhase(View v){

        zMatchPhase++;

        String displayText = "Start Match";
        int displayColor = darkThemeRed;
        if(zMatchPhase > 2) {
            zMatchPhase = 1;
        }

        switch (zMatchPhase){
            case 1:
                displayText = "Autonomous";
                displayColor = darkThemeBlue;
                scoringInfoEditable  = true;
                autoInforEditable    = true;
                rotationIntoEditable = false;
                endgameInfoEditable  = true;
                isAuto = true;
                saveEnabled = false;
                lConstraint.setBackgroundColor(backgroundBlue);
                updateBTarmac();
                break;


            case 2:
                displayText = "Tele-Operated";
                displayColor = darkThemeGreen;
                scoringInfoEditable  = true;
                autoInforEditable    = true;
                rotationIntoEditable = true;
                endgameInfoEditable  = true;
                lConstraint.setBackgroundColor(backgroundBlack);
                isAuto = false;
                saveEnabled = true;
                updateBTarmac();
                break;

            default:
                scoringInfoEditable  = true;
                autoInforEditable    = true;
                rotationIntoEditable = true;
                endgameInfoEditable  = true;
                saveEnabled = false;

                Toast.makeText(MainActivity.this, "The scouting app crashed. Tell James he screwed up.", Toast.LENGTH_LONG).show();
                break;
        }

        setAllElementEditability();
        updateScoringPortText();
        bMatchPhase.setText(displayText);
        bMatchPhase.setBackgroundColor(displayColor);
    }

    public void clickTarmac(View v){
        if(isAuto){
            zAutoTarmac = (zAutoTarmac + 1)%4;
            updateBTarmac();
        }
        else{
            zTarmac = (zTarmac + 1)%4;
            updateBTarmac();
        }
    }

    public void updateBTarmac()
    {
        if(isAuto) {
            bTarmac.setBackgroundColor( (zAutoTarmac == 0) ? darkThemeRed : darkThemeGreen);
            bTarmac.setText(climbDisplayText[zAutoTarmac]);
        }
        else {
            bTarmac.setBackgroundColor( (zTarmac == 0) ? darkThemeRed : darkThemeGreen);
            bTarmac.setText(climbDisplayText[zTarmac]);
        }
    }

    public void clickType(View v){
        String[] displayText = {"Defensive", "Offensive", "Transfer"};
        zType = (zType + 1)%3;

        bType.setBackgroundColor( (zType == 0) ? darkThemeRed : darkThemeGreen);
        bType.setText(displayText[zType]);
        updateScoringPortText();
    }

    public void clickPlusTopCone(View v){

        if(isAuto){
            zAutoTopCone++;
        } else {
            zTopCone++;
        }

        updateScoringPortText();
    }
    public void clickPlusBotCone(View v){

        if(isAuto){
            zAutoBotCone++;
        } else {
            zBotCone++;
        }

        updateScoringPortText();
    }

    public void clickMinusTopCone(View v){

        if(isAuto){
            zAutoTopCone = Math.max( (zAutoTopCone - 1), 0);
        } else {
            zTopCone = Math.max( (zTopCone - 1), 0);
        }

        updateScoringPortText();
    }

    public void clickMinusBotCone(View v){

        if(isAuto){
            zAutoBotCone = Math.max( (zAutoBotCone - 1), 0);
        } else {
            zBotCone = Math.max( (zBotCone - 1), 0);
        }

        updateScoringPortText();
    }
    public void clickPlusMidCone(View v){

        if(isAuto){
            zAutoMidCone++;
        } else {
            zMidCone++;
        }

        updateScoringPortText();
    }
    public void clickMinusMidCone(View v){

        if(isAuto){
            zAutoMidCone = Math.max( (zAutoMidCone - 1), 0);
        } else {
            zMidCone = Math.max( (zMidCone - 1), 0);
        }

        updateScoringPortText();
    }
    public void clickPlusTopCube(View v){

        if(isAuto){
            zAutoTopCube++;
        } else {
            zTopCube++;
        }

        updateScoringPortText();
    }
    public void clickPlusMidCube(View v){

        if(isAuto){
            zAutoMidCube++;
        } else {
            zMidCube++;
        }

        updateScoringPortText();
    }
    public void clickPlusBotCube(View v){

        if(isAuto){
            zAutoBotCube++;
        } else {
            zBotCube++;
        }

        updateScoringPortText();
    }
    public void clickMinusTopCube(View v){

        if(isAuto){
            zAutoTopCube = Math.max( (zAutoTopCube - 1), 0);
        } else {
            zTopCube = Math.max( (zTopCube - 1), 0);
        }

        updateScoringPortText();
    }
    public void clickMinusMidCube(View v){

        if(isAuto){
            zAutoMidCube = Math.max( (zAutoMidCube - 1), 0);
        } else {
            zMidCube = Math.max( (zMidCube - 1), 0);
        }

        updateScoringPortText();
    }
    public void clickMinusBotCube(View v){

        if(isAuto){
            zAutoBotCube = Math.max( (zAutoBotCube - 1), 0);
        } else {
            zBotCube = Math.max( (zBotCube - 1), 0);
        }

        updateScoringPortText();
    }

    //level 0 = low and level 3 = traversal

    public void clickMinusCollect(View v){

        zCollect = Math.max( (zCollect - 1), 0);

        updateScoringPortText();
    }

    public void clickPlusCollect(View v){

        zCollect++;

        updateScoringPortText();
    }

    public void clickRobotProblem(View v){

        String[] displayText = {"No Robot Problem", "Broken Parts", "Dead Partially", "Dead All Match"};
        int[] colorGradients = {darkThemeGreen, r2gGradient2, r2gGradient1, darkThemeRed};
        zRobotProblem = (zRobotProblem + 1)%4;
        bRobotProblem.setBackgroundColor(colorGradients[zRobotProblem]);
        bRobotProblem.setText(displayText[zRobotProblem]);
    }

    //public void clicksDock(View v){

   // }

    //public void clicksEngage(View v){

  //  }


    public void clickFoul(View v){

        String[] displayText = {"No Foul", "1-2 Fouls", "Too many (3+) Fouls"};
        int[] colorGradients = {darkThemeGreen, r2gGradient2, darkThemeRed};
        zFoul = (zFoul + 1)%3;
        bFoul.setBackgroundColor(colorGradients[zFoul]);
        bFoul.setText(displayText[zFoul]);
    }

    public void clickCard(View v){

        String[] displayText = {"No Card", "Yellow Card", "Red Card"};
        int[] colorGradients = {darkThemeGreen, r2gGradient2, darkThemeRed};
        zCard = (zCard + 1)%3;
        bCard.setBackgroundColor(colorGradients[zCard]);
        bCard.setText(displayText[zCard]);
    }

    public void clickCommunity(View v){

        if(isAuto) {
            if (leftCommunity == false) {
                leftCommunity = true;
            } else {
                leftCommunity = false;
            }
            sLeftCommunity.setChecked(leftCommunity);
        }
    }

    public void clickTeamNumber(View v) {
        if(!isEmpty(eMatchNumber) && !isEmpty(eScoutID))
        {
            zMatchNumber = Integer.parseInt(eMatchNumber.getText().toString());
            zScoutID = Integer.parseInt(eScoutID.getText().toString());
            setTeamNumber();
        }
    }

    public void clickSave(View v) throws IOException{

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String fileName = sharedPref.getString(FIlE_NAME_KEY, "");

        if (fileName.isEmpty()) {
            Toast.makeText(MainActivity.this, "Error: Honestly not really sure. Tell Jonathan to fix it or else he'll get kicked", Toast.LENGTH_LONG).show();

        } else if (isEmpty(eTeamNumber) || isEmpty(eMatchNumber) || isEmpty(eScoutID)){

            Toast.makeText(MainActivity.this, "Error: Match Number, Team Number, and Scout ID cannot be empty.", Toast.LENGTH_LONG).show();
        }  else if(!checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            Toast.makeText(MainActivity.this, "Cannot Write to Internal Storage, Tell Caleb He Screwed Up.", Toast.LENGTH_SHORT).show();
        } else {

            zSaveCount++;
            int[] colorGradients = {darkThemeGreen, r2gGradient3, r2gGradient2, r2gGradient1, darkThemeRed, darkThemeRed};
            int tapsLeft = 5 - zSaveCount;
            bSave.setBackgroundColor(colorGradients[zSaveCount]);
            if (tapsLeft > 1){
                bSave.setText("Hurry up! " + tapsLeft + " Times To Save / Reset");
            } else {
                bSave.setText("One more " + tapsLeft + " Time To Save / Reset");
                Toast.makeText(MainActivity.this, "behind you", Toast.LENGTH_LONG).show();
            }

            if (zSaveCount == 5) {

                zMatchNumber = Integer.parseInt(eMatchNumber.getText().toString());
                zScoutID = Integer.parseInt(eScoutID.getText().toString());
                zTeamNumber = Integer.parseInt(eTeamNumber.getText().toString());
                strDefenseRating = eDefenseRating.getText().toString();


                writeFile(fileName);

                zMatchNumber++;
                eMatchNumber.setText(Integer.toString(zMatchNumber));

                scoringInfoEditable = false;
                autoInforEditable = false;
                rotationIntoEditable = false;
                endgameInfoEditable = false;
                saveEnabled = false;
                isAuto = false;

                setAllElementEditability();
                zeroAllData();
                updateScoringPortText();
                setTeamNumber();
                bMatchPhase.setText("Start Match");
                bTarmac.setBackgroundColor(darkThemeRed);

                bRobotProblem.setText("No Robot Problem");
                bRobotProblem.setBackgroundColor(darkThemeGreen);
                bFoul.setText("No Foul");
                bFoul.setBackgroundColor(darkThemeGreen);
                bCard.setText("No Card");
                bCard.setBackgroundColor(darkThemeGreen);
                bSave.setText("Press 5 Times to Save / Reset");
                bSave.setBackgroundColor(darkThemeGreen);

                Toast.makeText(MainActivity.this, "Yay!!! File Saved :) Good luck on the next match!", Toast.LENGTH_SHORT).show();

            }

        }

    }

    private void writeFile(String fileName) throws IOException {


        File csvFile = Environment.getExternalStorageDirectory();
        csvFile = new File(csvFile, fileName);

        csvFile.setExecutable(true);
        csvFile.setReadable(true);
        csvFile.setWritable(true);

        BufferedWriter b = new BufferedWriter(new FileWriter(csvFile, true));

        if(zMatchNumber == 1)
        {
            writeString(b, "MatchNumber");
            writeString(b, "ScoutID");
            writeString(b, "AllianceColor");
            writeString(b, "TeamNumber");
            writeString(b, "AutoTopCube");
            writeString(b, "AutoMidCube");
            writeString(b, "AutoBotCube");
            writeString(b, "AutoTopCone");
            writeString(b, "AutoMidCone");
            writeString(b, "AutoBotCone");
            writeString(b, "TopCube");
            writeString(b, "MidCube");
            writeString(b, "BotCube");
            writeString(b, "TopCone");
            writeString(b, "MidCone");
            writeString(b, "BotCone");
            writeString(b, "Climb");
            writeString(b, "Auto Climb");
            writeString(b, "Collect");
            writeString(b, "Robot Problem");
            writeString(b, "Foul");
            writeString(b, "Card");
            writeString(b, "Left Community");
            writeString(b, "Rating");
            writeString(b, "Defense Rating");
            writeString(b, "Robot Type");
            b.append("\n");

        }
        String[] displayText = {"No Climb", "Touching", "Leveled", "In Community"};
        String[] displayTypeText = {"Offensive", "Transfer", "Defensive"};
        String[] displayTextProblem = {"No Robot Problem", "Broken Parts", "Dead Partially", "Dead All Match"};
        write(b, zMatchNumber);
        write(b, zScoutID);
        write(b, zAllianceColor);
        write(b, zTeamNumber);
        write(b, zAutoTopCube);
        write(b, zAutoMidCube);
        write(b, zAutoBotCube);
        write(b, zAutoTopCone);
        write(b, zAutoMidCone);
        write(b, zAutoBotCone);
        write(b, zTopCube);
        write(b, zMidCube);
        write(b, zBotCube);
        write(b, zTopCone);
        write(b, zMidCone);
        write(b, zBotCone);
        writeString(b, displayText[zAutoTarmac]);

        writeString(b, displayText[zTarmac]);

        write(b, zCollect);
        writeString(b, displayTextProblem[zRobotProblem]);
        write(b, zFoul);
        write(b, zCard);
        writeBoolean(b, leftCommunity);
        writeFloat(b, rDefenseBar.getRating());
        writeString(b, strDefenseRating);
        writeString(b, displayTypeText[zType]);

        b.append("\n");
        b.close();

        MediaScannerConnection.scanFile(this, new String[] {csvFile.toString()}, null, null);
    }

    private boolean checkPermission(String permission){
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }

    private void write(BufferedWriter b, int i){
        try {
            b.append(i + ",");
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void writeString(BufferedWriter b, String s){
        try {
            b.append(s + ",");
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void writeBoolean(BufferedWriter b, Boolean bo){
        try {
            b.append(bo + ",");
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void writeFloat(BufferedWriter b, Float f){
        try {
            b.append(f + ",");
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private HashMap<Integer, List<String>> parseMatchFile() {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream inputStream =
                     getResources().openRawResource(R.raw.matchdata);
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            //Cannot parse file and populate team numbers
        }
        String stringMatches = stringBuilder.toString();

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<Integer, List<String>>> typeReference = new TypeReference<HashMap<Integer, List<String>>>() {};
        try {
            return mapper.readValue(stringMatches, typeReference);
        } catch (IOException e) {
            return new HashMap<>();
        }
    }

    private void setTeamNumber() {
        try {
            zTeamNumber = Integer.parseInt(matchMap.get(zMatchNumber).get(zScoutID - 1).substring(3));
            eTeamNumber.setText(String.format(Integer.toString(zTeamNumber)));
        }
        catch (Exception e)
        {
            //Don't throw an exception if we can't populate the Team Numbers
            eTeamNumber.setText("");
        }
    }

}
//"a useless program created by Jonathan Chu" - Gavin Wan
