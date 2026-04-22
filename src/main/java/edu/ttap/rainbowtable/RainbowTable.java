package edu.ttap.rainbowtable;

import java.util.function.Function;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Optional;

/**
 * A rainbow table is a collection of chains of passwords for the purpose of
 * reversing hashes.
 */
public class RainbowTable {

    // need to store three things for invert 
    // the reverse-lookup, so End password, Start passsword
    // HashMap for fast look up ( 0(1)) 
    // 

    private Map<Password, Password> endToStart;

    // password -> hash
    private Function<Password, Hash> hash;
    
    
    // now hash ->passwords, make some passwords for a hash
    private Function<Hash, Password> reducer; 


    /**
     * Constructs a new rainbow table from an already-computed list of endpoints.
     * @param chains a list of password chains, pairs of starting and ending passwords.
     * @param hasher a function that maps a password to its hash
     * @param reducer a function that maps a hash to its password
     */
    public RainbowTable(
            List<Pair<Password, Password>> chains,
            Function<Password, Hash> hasher,
            Function<Hash, Password> reducer) {

        // TODO: implement me!
        // save the hash and reducer
        this.hash = hasher; 
        this.reducer = reducer;

        // build end->start lookup map
        this.endToStart = new HashMap<>();
        
        for (Pair<Password, Password> chain : chains) {
            //chain.first is start passwords and 
            // chain.second is end password
            // key by end

            endToStart.put(chain.second(), chain.first());
        }
    }

    /**
     * Attempts to reverse the given hash according to the rainbow table algorithm.
     * 
     * @param h the hash to invert
     * @param maxSteps the maximum number of steps (hash-reduce cycles) to attempt
     * @return an Optional containing the password if found, or empty if not
     */
    public Optional<Password> invert(Hash h, int maxSteps) {
        // TODO: implement me!
        // walk forwad from h, till known endpoint

        Hash currentHash = h;

        for( int step = 0; step < maxSteps; step++) {
            // reduc current hash to a password

            Password pass = reducer.apply(currentHash);
            // look if pass is the end

            if(endToStart.containsKey(pass)) {
                // found a matching endpoint
                // walk from start to find passwords that made h. 
                
                // 
                Password chainStart = endToStart.get(pass);

                Password currentPassword = chainStart;

            }
        }
        return Optional.empty();
    }
}


while (true) {
                    
                    Hash nextHash = hash.apply(currentPassword);

                    //so og hash h?
                    if(nextHash.equals(h)) {
                        return Optional.of(currentPassword);
                    }

                    // its not

                    currentPassword = reducer.apply(nextHash);
                }

            }
            // not an endpoint

            currentHash = hash.apply(pass);
        }
        // maxStep withouth finding
        return Optional.empty();
    }