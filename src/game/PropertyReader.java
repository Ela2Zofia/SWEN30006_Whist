package game;
import java.util.Properties;
import java.io.FileReader;
import java.io.IOException;


public class PropertyReader{

    private static PropertyReader propertyReader = null;

    private int nbPlayers;
    private int nbNPC;
    private int nbStartCards;
    private int winningScore;
    private int randomState;
    private boolean rule;

    private NPC[] npcs;
    private int[] players;

    public int getNbPlayers() {
        return nbPlayers;
    }

    public int getNbNPC() {
        return nbNPC;
    }

    public int getNbStartCards() {
        return nbStartCards;
    }

    public int getWinningScore() {
        return winningScore;
    }

    public int getRandomState() {
        return randomState;
    }

    public NPC[] getNPCs(){
        return npcs;
    }

    public int[] getPlayers(){
        return players;
    }

    public boolean getRule(){
        return rule;
    }


    private PropertyReader(String propertyName) throws IOException {
        Properties properties = new Properties();

        FileReader inStream = null;
        try {
            inStream = new FileReader(propertyName);
            properties.load(inStream);
        } finally {
            if (inStream != null) {
                inStream.close();
            }
        }

        nbPlayers = Integer.parseInt(properties.getProperty("nbPlayers"));
        nbStartCards = Integer.parseInt(properties.getProperty("nbStartCards"));
        winningScore = Integer.parseInt(properties.getProperty("winningScore"));
        randomState = Integer.parseInt(properties.getProperty("randomState"));
        nbNPC = Integer.parseInt(properties.getProperty("nbNPC"));
        rule = Boolean.parseBoolean(properties.getProperty("rule"));
        npcs = new NPC[nbNPC];
        players = new int[nbPlayers];
        for (int i = 0; i < nbNPC; i++){
            int k = i+1;
            String[] list = properties.getProperty("npc" + k).split(",");
            npcs[i] = new NPC(list[0], list[1]);
        }
        for (int i = 0; i < nbPlayers; i++){
            players[i] = 0;
        }
        for (int i = 0; i < (nbPlayers-nbNPC); i++){
            players[i] = 1;
        }
    }


    private PropertyReader() throws IOException{
        Properties properties = new Properties();

        FileReader inStream = null;
        try {
            inStream = new FileReader("whist.properties");
            properties.load(inStream);
        } finally {
            if (inStream != null) {
                inStream.close();
            }
        }
        nbPlayers = Integer.parseInt(properties.getProperty("nbPlayers"));
        nbStartCards = Integer.parseInt(properties.getProperty("nbStartCards"));
        winningScore = Integer.parseInt(properties.getProperty("winningScore"));
        randomState = Integer.parseInt(properties.getProperty("randomState"));
        nbNPC = Integer.parseInt(properties.getProperty("nbNPC"));
        rule = Boolean.parseBoolean(properties.getProperty("rule"));

        npcs = new NPC[nbNPC];
        players = new int[nbPlayers];
        for (int i = 0; i < nbNPC; i++){
            String[] list = properties.getProperty("npc"+(i+1)).split(",");
            npcs[i] = new NPC(list[0], list[1]);

        }
        for (int i = 0; i < nbPlayers-nbNPC; i++){
            players[i] = 1;
        }

    }


    public static PropertyReader getInstance() throws IOException {
        if (propertyReader == null) {
            propertyReader = new PropertyReader();
        }
        return propertyReader;
    }

    public static PropertyReader getInstance(String propertyName) throws IOException{
        if (propertyReader == null){
            propertyReader = new PropertyReader(propertyName);
        }
        return propertyReader;
    }

}
