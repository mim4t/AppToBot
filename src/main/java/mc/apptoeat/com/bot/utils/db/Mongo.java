package mc.apptoeat.com.bot.utils.db;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import mc.apptoeat.com.bot.main;
import org.bson.Document;

@Getter
public class Mongo {
    private boolean enabled = true;
    private MongoClient client;
    private MongoDatabase database;
    private MongoCollection<Document> players;
    private MongoCollection<Document> difficulties;
    private MongoCollection<Document> unranked;
    private MongoCollection<Document> ranked;
    private MongoCollection<Document> tournament;




    public Mongo() {
        main m = main.getInstance();
        this.client = MongoClients.create(m.getConfig().getString("mongo.uri"));
        this.database = client.getDatabase(m.getConfig().getString("mongo.database"));

        players = database.getCollection("players");
        difficulties = database.getCollection("difficulties");
        unranked = database.getCollection("unranked");
        ranked = database.getCollection("ranked");
        tournament = database.getCollection("tournament");
    }

    public void shutdown() {
        try {
            Thread.sleep(50L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        this.client.close();
    }
}
