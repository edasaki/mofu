package com.edasaki.misakachan.source;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.edasaki.misakachan.chapter.Chapter;
import com.edasaki.misakachan.chapter.Series;

public abstract class AbstractSource {

    /**
     * @param url the url to match
     * @return <code>true</code> if the given URL matches this source's URL pattern
     */
    public abstract boolean match(String url);

    /**
     * @param url the url to match
     * @return <code>true</code> if the given URL matches this source's URL pattern for main pages of manga
     */
    public abstract boolean matchInfo(String url);

    public abstract Series getSeries(String url);

    public abstract Chapter getChapter(String url);

    public abstract String getSourceName();

    public abstract SearchAction getSearch();

    @Override
    public String toString() {
        return this.getSourceName();
    }

    protected SearchResultSet createResultSet() {
        return new SearchResultSet(getSourceName());
    }

    protected SearchResultSet createResultSet(Collection<SearchResult> results) {
        SearchResultSet set = createResultSet();
        set.addResults(results);
        return set;
    }

    protected SearchResultSet createResultSet(Map<Element, List<String>> results) {
        List<SearchResult> res = new ArrayList<SearchResult>();
        for (Entry<Element, List<String>> e : results.entrySet()) {
            SearchResult sr = new SearchResult(e.getValue().get(0), e.getValue().subList(1, e.getValue().size()), e.getKey().absUrl("href"));
            res.add(sr);
        }
        return createResultSet(res);
    }

    protected String selectFirst(Element ele, String selector) {
        Elements e = ele.select(selector);
        if (e.isEmpty())
            return "";
        return e.first().text();
    }
}
