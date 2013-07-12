/*
 */
package experiment.mutation2013;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author chris
 */
public class MutantTableMap {

    private HashMap<String, Set<String>> map;

    public MutantTableMap() {
        map = new HashMap<>();
    }

    public void addMutant(String table, String mutantTable) {
        Set<String> set;
        if (map.containsKey(table)) {
            set = map.get(table);
        } else {
            set = new LinkedHashSet<>();
            map.put(table, set);
        }
        set.add(mutantTable);
    }

    public Set<String> getMutants(String table) {
        if (map.containsKey(table)) {
            return map.get(table);
        } else {
            return new HashSet<>();
        }
    }
}
