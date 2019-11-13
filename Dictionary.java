package GameComponents;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Dictionary {
	
    static Set<String> dictionarySet = new HashSet<String>(Arrays.asList("a", "able", "above", "afraid", "after", "again", "always",
            "among", "appear", "answer", "baby", "back", "band", "ball", "basic", "beauty", "before", "began",
            "between", "bird", "black", "blood", "blue", "board", "bottom", "busy", "came",
            "captain", "car", "carry", "catch", "caught", "cause", "cell", "certain", "character", "claim",
            "clean", "clear", "climb", "company", "common", "complete", "control", "corn", "correct", "copy",
            "cotton", "count", "create", "current", "dance", "danger", "decide", "depend", "determine", "dictionary",
            "difficult", "discuss", "distant", "division", "dollar", "door", "draw", "dream", "dress", "drink", "during",
            "edge", "egg", "element", "enemy", "energy", "engine", "experience", "exercise", "experiment", "famous",
            "favor", "fear", "field", "field"));
    
    public Dictionary() {
    	this.dictionarySet = dictionarySet;
    }
    
    public boolean isValidWord(String word) {
    	return dictionarySet.contains(word);
    }
    
    

}
