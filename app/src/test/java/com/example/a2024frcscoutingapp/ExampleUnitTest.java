package com.example.a2024frcscoutingapp;

import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.thebluealliance.api.v3.TBA;
import com.thebluealliance.api.v3.models.SimpleMatch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void tba_test() throws IOException
    {
        // Get Match Data.
        TBA api = new TBA("AdKpprz7naDhuNjNCAqgzT8QY46m9jBTEgHNNUMiSH5qfUefZwAWHdbomLHagWU6");
        SimpleMatch[] simpleMatches = api.eventRequest.getSimpleMatches("2023wasam");
        System.out.println(Arrays.toString(simpleMatches));

        // Convert match data to a format that can be parsed.
        Map<Integer, List<String>> matchMap = ConvertSimpleMatchesToMap(simpleMatches);

        // Write to file
        File file = new File("src/main/res/raw/matchdata.txt");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(matchMap);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(json);
        writer.close();

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<Integer, List<String>>> typeReference = new TypeReference<HashMap<Integer, List<String>>>() {};
        Map<Integer, List<String>> reloadedMatches = mapper.readValue(file, typeReference);
        System.out.println(ow.writeValueAsString(reloadedMatches));
    }

    private Map<Integer, List<String>> ConvertSimpleMatchesToMap(SimpleMatch[] simpleMatches) {
        Map<Integer, List<String>> matchesToTeams = new HashMap<>();

        for (SimpleMatch simpleMatch : simpleMatches)
        {
            if (simpleMatch.getCompLevel().equals("qm")) {
                SimpleMatch.Alliance blueAlliance = simpleMatch.getBlueAlliance();
                SimpleMatch.Alliance redAlliance = simpleMatch.getRedAlliance();

                List<String> teams = new ArrayList<>();
                teams.addAll(Arrays.asList(blueAlliance.getTeamKeys()));
                teams.addAll(Arrays.asList(redAlliance.getTeamKeys()));
                matchesToTeams.put(simpleMatch.getMatchNumber(), teams);
            }
        }

        for (Map.Entry<Integer, List<String>> match : matchesToTeams.entrySet()) {
            for (int i = 0; i < match.getValue().size(); i++) {
                System.out.printf("Match #%d: Team#%d: %s\n", match.getKey(), i, match.getValue().get(i));
            }
        }

        System.out.printf("Match 1, Team 1: %s", matchesToTeams.get(1).get(0));
        return matchesToTeams;
    }

    @Test
    public void test_code()
    {
    }


    class MatchInfo
    {
        private int matchNumber;
        private List<Integer> teamNumber;
        private List<String> station;
    }

    class TeamMatchInfo
    {
        private int matchNumber;
        private int teamNumber;
        private String station;
    }

    class MatchScoutInfo
    {
        private int matchNumber;
        private String station;

        public MatchScoutInfo(int matchNumber, String station)
        {
            this.matchNumber = matchNumber;
            this.station = station;
        }
    }

    private Map<MatchScoutInfo, Integer> getMatchInfoToTeamNumberMap() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        //TODO: Get file name
        List<MatchInfo> matchInfoList = mapper.readValue(new File("sampleFile"), new TypeReference<List<MatchInfo>>(){});

        //TODO: Convert List of MatchInfo to Map of MatchScoutInfo -> Integer
        return null;
    }

    private void experiment() throws IOException
    {
        TBA api = new TBA("AdKpprz7naDhuNjNCAqgzT8QY46m9jBTEgHNNUMiSH5qfUefZwAWHdbomLHagWU6");
        api.eventRequest.getSimpleMatches("2023wasno");
    }
}