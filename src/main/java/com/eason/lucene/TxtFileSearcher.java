package com.eason.lucene;

import java.io.File;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * This class is used to demonstrate the process of searching on an existing
 * Lucene index
 * 
 */
public class TxtFileSearcher {
	public static void main(String[] args) throws Exception {
		String queryStr = "Lucene";
		// This is the directory that hosts the Lucene index
		File indexDir = new File("C:\\lucene\\index");
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_40);
		Directory index = FSDirectory.open(indexDir);
		DirectoryReader ireader = DirectoryReader.open(index);
		IndexSearcher isearcher = new IndexSearcher(ireader);

		QueryParser parser = new QueryParser(Version.LUCENE_40, "content", analyzer);
		Query query = parser.parse(queryStr);

		ScoreDoc[] hits = isearcher.search(query, null, 1000).scoreDocs;

		for (int i = 0; i < hits.length; i++) {
			Document hitDoc = isearcher.doc(hits[i].doc);
			System.out.println("____________________________");
			System.out.println(hitDoc.get("filename"));
			System.out.println(hitDoc.get("content"));
			System.out.println(hitDoc.get("path"));
			System.out.println("____________________________");
		}
		ireader.close();
		index.close();
	}
}
