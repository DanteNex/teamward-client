package fr.neamar.lolgamedata.pojo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class ChampionCounter implements Serializable {
    public String name = null;
    public int id;
    public String image;
    public int mastery = -1;
    public int winRate = -1;
    public String ggURL;

    public ChampionCounter(JSONObject champion, boolean isCounter) throws JSONException {
        image = champion.getString("image");
        name = champion.getString("name");
        id = champion.getInt("id");
        ggURL = champion.getString("gg");
        if (isCounter) {
            mastery = champion.getInt("mastery");
            winRate = champion.getInt("winRate");
        }
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ChampionCounter)) {
            return obj instanceof String && obj.equals(name);

        }

        return name.equals(((ChampionCounter) obj).name);
    }
}
