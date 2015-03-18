package org.couchbase.advocacy.metrics.twitter;

import com.couchbase.client.protocol.views.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

/**
 * Created by ldoguin on 18/03/15.
 */
public interface TwitterUpdateRepository extends CrudRepository<TwitterUpdate, String> {

    Collection<TwitterUpdate> findByDate(Query query);

    Collection<TwitterUpdate> findFollowersByDate(Query query);
}
