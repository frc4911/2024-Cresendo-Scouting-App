package com.example.a2024frcscoutingapp;
//idk how to change the file name to 2023 frc
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
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

//import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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
import java.util.Random;

//ignore all the commented out things :)
public class MainActivity extends AppCompatActivity implements FileNotif {


    private VideoView vid;
    private MediaController m;
    
    
    private String event_Id;

    private int match_Id;

    private int Team_Number;

    private int scout_Id;

    private int Intake;

    private boolean Tipped;

    private int score_amp_tele;

    private int score_speaker_tele;

    private boolean leave_auto;

    private int score_amp_auto;

    private int score_speaker_auto;

    /////new variables
    private int trap;



    private boolean melody;

    private String Climb_end = "C";

    private String Park_end = "P";

    private String Nothing_end = "N";

    private boolean Card_red;

    private boolean Card_yellow;

    private boolean disabled;


    private boolean no_robot_problem;
    private boolean broken_parts;
    private boolean partially_dead;


    public static final String FIlE_NAME_KEY = "standard";

    // private static RatingBar rDefenseBar;

    // private String strDefenseRating;
    private Switch sLeftCommunity;
    private Switch sSpotlight;
    private Switch sMelody;

    private View lConstraint;

    private EditText eScoutID;
    private EditText eTeamNumber;
    private EditText eMatchNumber;
    //private static EditText eDefenseRating;

    private Button bMatchPhase;
    private static Button bTarmac;
    private Button bMinusSpeaker;
    private Button bMinusTrap;
    private Button bMinusAmp;

    private Button bPlusSpeaker;
    private Button bPlusTrap;
    private Button bPlusAmp;

    private Button bRobotProblem;

    private Button bPlusCollect;
    private Button bMinusCollect;

    private Button bFoul;

    private Button bCard;

    //private Button bType;

    private Button bSave;
    private Button bRoullete1, bRoullete2, bRoullete3, bRoullete4, bRoullete5, bRoullete6;

    private TextView tSpeaker, tAmp, tTrap, tCollect, textView;
    //private ImageView imageView;

    private static boolean leftCommunity, zMelody, zSpotlight;
    
    private static int zMatchNumber, zScoutID, zAllianceColor,
            zTeamNumber,
            zMatchPhase, zTarmac,zAutoTarmac,
            zAutoSpeaker, zAutoTrap, zAutoAmp,
            zSpeaker, zTrap, zAmp,
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


    private String[] climbDisplayText = {"No Climb", "Park", "Climb", "Spotlight Climb"};

    private Map<Integer, List<String>> matchMap = new HashMap<>();




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestAppPermissions();
        readFileName();
        setContentView(R.layout.activity_main);

        lConstraint = findViewById(R.id.lConstraint);

        // = findViewById(R.id.eMatchNumber);

        eScoutID = findViewById(R.id.eScoutID);
        eTeamNumber = findViewById(R.id.eTeamNumber);
        eMatchNumber = findViewById(R.id.eMatchNumber);
        //bAllianceColor = findViewById(R.id.bAllianceColor);
        bMatchPhase = findViewById(R.id.bMatchPhase);
        bTarmac = findViewById(R.id.bInitiationLine);
        bPlusAmp = findViewById(R.id.bPlusAmp);
        bMinusAmp = findViewById(R.id.bMinusAmp);
        bMinusSpeaker = findViewById(R.id.bMinusSpeaker);
        bPlusSpeaker = findViewById(R.id.bPlusSpeaker);
        bPlusTrap = findViewById(R.id.bPlusTrap);
        bMinusTrap = findViewById(R.id.bMinusTrap);

        bRoullete1 = findViewById(R.id.bRoullete1);
        bRoullete2 = findViewById(R.id.bRoullete2);
        bRoullete3 = findViewById(R.id.bRoullete3);
        bRoullete4 = findViewById(R.id.bRoullete4);
        bRoullete5 = findViewById(R.id.bRoullete5);
        bRoullete6 = findViewById(R.id.bRoullete6);
        textView = findViewById(R.id.textView);
        //imageView = findViewById(R.id.imageView);
        bRobotProblem = findViewById(R.id.bRobotProblem);

        bFoul = findViewById(R.id.bFoul);
        bPlusCollect = findViewById(R.id.bPlusCollect);
        bMinusCollect = findViewById(R.id.bMinusCollect);
        sLeftCommunity = findViewById(R.id.sLeftCommunity);


        bCard = findViewById(R.id.bCard);
        bSave = findViewById(R.id.bSave);

        tSpeaker = findViewById(R.id.tSpeaker);
        tTrap = findViewById(R.id.tTrap);
        tAmp = findViewById(R.id.tAmp);
        tCollect = findViewById(R.id.tCollect);


        zeroAllData();

        setAllElementEditability();

        matchMap = parseMatchFile();
    }

    private void readFileName() {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String defaultValue = sharedPref.getString(FIlE_NAME_KEY, "PLEASE SET FILENAME");
        setTitle(defaultValue);
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

    private void zeroAllData() {

        zTeamNumber = 0;
        zMatchPhase = 0;
        zTarmac = 0;
        zAutoTarmac = 0;
        zAutoSpeaker = 0;
        zAutoAmp = 0;
        zAutoTrap = 0;
        zSpeaker = 0;
        zAmp = 0;
        zTrap = 0;
        //zType = 0;
        zRobotProblem = 0;
        zFoul = 0;
        zCard = 0;
        zSaveCount = 0;
        zCollect = 0;
        leftCommunity = false;
        sLeftCommunity.setChecked(leftCommunity);
        // bType.setText("Robot Type");
        bTarmac.setText("Climb: None");
        //rDefenseBar.setRating(0);
        //eDefenseRating.setText("Defense Rating");
    }

    private void setAllElementEditability() {

        setButtonEditable(bTarmac, scoringInfoEditable);

        setButtonEditable(bMinusAmp, scoringInfoEditable);
        setButtonEditable(bPlusAmp, scoringInfoEditable);
        setButtonEditable(bMinusSpeaker, scoringInfoEditable);
        setButtonEditable(bPlusSpeaker, scoringInfoEditable);

        setButtonEditable(bMinusTrap, scoringInfoEditable);
        setButtonEditable(bPlusTrap, scoringInfoEditable);
        setButtonEditable(bPlusCollect, scoringInfoEditable);
        setButtonEditable(bMinusCollect, scoringInfoEditable);



        setButtonEditable(bSave, saveEnabled);
    }

    private void setButtonEditable(Button b, Boolean enabled) {

        b.setEnabled(enabled);
        b.setTextColor(enabled ? Color.WHITE : Color.GRAY);
    }

    private void updateScoringPortText() {

        int upperDisplaySpeaker, lowerDisplayTrap, middleDisplayAmp,collect;
        if(isAuto){
            upperDisplaySpeaker = zAutoSpeaker;
            lowerDisplayTrap = zAutoTrap;
            middleDisplayAmp = zAutoAmp;
            collect = zCollect;


        } else {

            upperDisplaySpeaker = zSpeaker;
            lowerDisplayTrap = zTrap;
            middleDisplayAmp = zAmp;
            collect = zCollect;
            
        }
        tSpeaker.setText("Speaker = " + upperDisplaySpeaker);
        tTrap.setText("Trap = " + lowerDisplayTrap);
        tAmp.setText("Amp = " + middleDisplayAmp);
        tCollect.setText("Collect: " + collect);
    }

    private boolean isEmpty(EditText etText) { // a method to check if an edit text is empty

        return etText.getText().toString().trim().length() == 0;

    }

    public int Roulette()
    {
        final int random = new Random().nextInt((6)+1);
        return random;
    }
    private void noMoreFun()
    {
        bRoullete1.setVisibility(View.INVISIBLE);
        bRoullete2.setVisibility(View.INVISIBLE);
        bRoullete3.setVisibility(View.INVISIBLE);
        bRoullete4.setVisibility(View.INVISIBLE);
        bRoullete5.setVisibility(View.INVISIBLE);
        bRoullete6.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.INVISIBLE);
    }

    private void moreFun()
    {
        bRoullete1.setVisibility(View.VISIBLE);
        bRoullete2.setVisibility(View.VISIBLE);
        bRoullete3.setVisibility(View.VISIBLE);
        bRoullete4.setVisibility(View.VISIBLE);
        bRoullete5.setVisibility(View.VISIBLE);
        bRoullete6.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);
    }


    public void clickMatchPhase(View v){

        zMatchPhase++;
        String displayText = "Start Match";
        int displayColor = darkThemeRed;
        moreFun();

        if(zMatchPhase > 2) {
            zMatchPhase = 1;
        }

        switch (zMatchPhase){
            case 1:
                noMoreFun();
                displayText = "Autonomous";
                displayColor = darkThemeBlue;
                scoringInfoEditable  = true;
                autoInforEditable    = true;
                rotationIntoEditable = false;
                endgameInfoEditable  = true;
                isAuto = true;
                saveEnabled = false;
                lConstraint.setBackgroundColor(backgroundBlue);
                sLeftCommunity.setVisibility(View.VISIBLE);
                bTarmac.setVisibility(View.INVISIBLE);
                updateBTarmac();
                break;


            case 2:
                noMoreFun();
                displayText = "Tele-Operated";
                displayColor = darkThemeGreen;
                scoringInfoEditable  = true;
                autoInforEditable    = true;
                rotationIntoEditable = true;
                endgameInfoEditable  = true;
                lConstraint.setBackgroundColor(backgroundBlack);
                isAuto = false;
                saveEnabled = true;
                sLeftCommunity.setVisibility(View.INVISIBLE);
                bTarmac.setVisibility(View.VISIBLE);
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
            zAutoTarmac = (zAutoTarmac + 1)% climbDisplayText.length;
            updateBTarmac();
        }
        else{
            zTarmac = (zTarmac + 1)% climbDisplayText.length;
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

        //bType.setBackgroundColor( (zType == 0) ? darkThemeRed : darkThemeGreen);
        //bType.setText(displayText[zType]);
        updateScoringPortText();
    }

    public void clickPlusSpeaker(View v){

        if(isAuto){
            zAutoSpeaker++;
        } else {
            zSpeaker++;
        }

        updateScoringPortText();
    }
    public void clickPlusAmp(View v){

        if(isAuto){
            zAutoAmp++;
        } else {
            zAmp++;
        }

        updateScoringPortText();
    }

    public void clickMinusSpeaker(View v){

        if(isAuto){
            zAutoSpeaker = Math.max( (zAutoSpeaker - 1), 0);
        } else {
            zSpeaker = Math.max( (zSpeaker - 1), 0);
        }

        updateScoringPortText();
    }

    public void clickMinusAmp(View v){

        if(isAuto){
            zAutoAmp = Math.max( (zAutoAmp - 1), 0);
        } else {
            zAmp = Math.max( (zAmp - 1), 0);
        }

        updateScoringPortText();
    }
    public void clickPlusTrap(View v){

        if(isAuto){
            zAutoTrap++;
        } else {
            zTrap++;
        }

        updateScoringPortText();
    }
    public void clickMinusTrap(View v){

        if(isAuto){
            zAutoTrap = Math.max( (zAutoTrap - 1), 0);
        } else {
            zTrap = Math.max( (zTrap - 1), 0);
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

    public void clickMelody(View v){
        zMelody = !zMelody;
        sMelody.setChecked(zMelody);
        }
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

    public void clickSpotlight(View v){

        if(isAuto) {
            if (zSpotlight == false) {
                zSpotlight = true;
            } else {
                zSpotlight = false;
            }
            sSpotlight.setChecked(zSpotlight);
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

    public void clickRoulette1(View v)
    {
        if(Roulette() == 1)
        {
            bRoullete1.setBackgroundColor(darkThemeRed);
        }
    }

    public void clickRoulette2(View v)
    {
        if(Roulette() == 1)
        {
            bRoullete2.setBackgroundColor(darkThemeRed);
        }
    }
    public void clickRoulette3(View v)
    {
        if(Roulette() == 1)
        {
            bRoullete3.setBackgroundColor(darkThemeRed);
        }
    }
    public void clickRoulette4(View v)
    {
        if(Roulette() == 1)
        {
            bRoullete4.setBackgroundColor(darkThemeRed);
        }
    }
    public void clickRoulette5(View v)
    {
        if(Roulette() == 1)
        {
            bRoullete5.setBackgroundColor(darkThemeRed);
        }
    }

    public void clickRoulette6(View v)
    {
        if(Roulette() == 1)
        {
            bRoullete6.setBackgroundColor(darkThemeRed);
        }
    }

    private void requestAppPermissions() {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            return;
        }

        if (hasReadPermissions() && hasWritePermissions()) {
            return;
        }

        ActivityCompat.requestPermissions(this,
                new String[] {
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 112); // your request code
    }

    private boolean hasReadPermissions() {
        return (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private boolean hasWritePermissions() {
        return (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }


    public void clickSave(View v) throws IOException{

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String fileName = sharedPref.getString(FIlE_NAME_KEY, "");

        if (fileName.isEmpty()) {
            Toast.makeText(MainActivity.this, "Error: Honestly not really sure. Be sure you made a filename. Tell Jonathan to fix it or else he'll get kicked", Toast.LENGTH_LONG).show();

        } else if (isEmpty(eTeamNumber) || isEmpty(eMatchNumber) || isEmpty(eScoutID)){

            Toast.makeText(MainActivity.this, "Error: Match Number, Team Number, and Scout ID cannot be empty.", Toast.LENGTH_LONG).show();
        }  else if(!checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            Toast.makeText(MainActivity.this, "Cannot Write to Internal Storage, Tell Caleb He Screwed Up.", Toast.LENGTH_SHORT).show();
        }
        else {

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
                //strDefenseRating = eDefenseRating.getText().toString();


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

    /*public void videoView3(VideoView v)
    {
        Uri uri=Uri.parse("www.abc.com/myVid.mp4");
        VideoView videoView = (VideoView) findViewById(R.id.videoView);
        videoView.setVideoURI(uri);
    }*/
    private void writeFile(String fileName) throws IOException {


        File csvFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);//Environment.getExternalStorageDirectory();
        csvFile = new File(csvFile, fileName);

        csvFile.setExecutable(true);
        csvFile.setReadable(true);
        csvFile.setWritable(true);

        BufferedWriter b = new BufferedWriter(new FileWriter(csvFile, true));

        /*if(zMatchNumber == 1)
        {
            writeString(b, "MatchNumber");
            writeString(b, "ScoutID");
            writeString(b, "AllianceColor");
            writeString(b, "TeamNumber");
            writeString(b, "score_amp_auto");
            writeString(b, "score_speaker_auto");
            writeString(b, "score_trap_auto");
            writeString(b, "score_amp_tele");
            writeString(b, "score_speaker_tele");
            writeString(b, "score_trap_tele");
            writeString(b, "Climb");
            writeString(b, "Collect");
            writeString(b, "Robot Problem");
            writeString(b, "Foul");
            writeString(b, "Card");
            writeString(b, "Left Community");
            //writeString(b, "Rating");
            //writeString(b, "Defense Rating");
            //writeString(b, "Robot Type");
            b.append("\n");

        }*/

        String[] displayText = {"No Climb", "Park", "Climb", "Spotlight Climb"};
        int[] climbPoints = {0, 1, 3, 4};
        String[] displayTypeText = {"Offensive", "Transfer", "Defensive"};
        String[] displayTextProblem = {"No Robot Problem", "Broken Parts", "Dead Partially", "Dead All Match"};
        write(b, zMatchNumber);
        write(b, zScoutID);
        write(b, zAllianceColor);
        write(b, zTeamNumber);
        write(b, zAutoAmp);
        write(b, zAutoSpeaker);
        write(b, zAutoTrap);
        write(b, zAmp);
        write(b, zSpeaker);
        write(b, zTrap);
        write(b, climbPoints[zTarmac]);
        write(b, zCollect);
        writeString(b, displayTextProblem[zRobotProblem]);
        write(b, zFoul);
        write(b, zCard);
        if (leftCommunity) {
            write(b, 2);
        }
        else {
            write(b, 0);
        }
        //writeBoolean(b, leftCommunity);
        //writeFloat(b, rDefenseBar.getRating());
        //writeString(b, strDefenseRating);
        //writeString(b, displayTypeText[zType]);

        /*write(b, zAutoTopCube);
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
        */

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

    @Override
    public void onFileChanged() {
        readFileName();
    }
}
//"a useless program created by Jonathan Chu" - Gavin Wan
