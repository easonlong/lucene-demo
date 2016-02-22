package com.eason.lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * This class demonstrate the process of creating index with Lucene for text
 * files
 */
public class TxtFileIndexer {
	public static void main(String[] args) throws Exception {
		File indexDir = new File("C:\\lucene\\index");
		File dataDir = new File("C:\\lucene\\data\\txt");
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_40);
		File[] dataFiles = dataDir.listFiles();
		Directory index = FSDirectory.open(indexDir);
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_40, analyzer);
		IndexWriter indexWriter = new IndexWriter(index, config);
		long startTime = new Date().getTime();
		for (File file : dataFiles) {
			if (file.isFile() && file.getName().endsWith(".txt")) {
				System.out.println("Indexing file " + file.getCanonicalPath());
				Document document = new Document();
				document.add(new TextField("path", file.getCanonicalPath(), Store.YES));
				document.add(new TextField("filename", file.getName(), Store.YES));
				document.add(new TextField("content", txt2String(file), Store.YES));
				indexWriter.addDocument(document);
			}
		}
		indexWriter.commit();
		indexWriter.close();
		long endTime = new Date().getTime();

		System.out.println("It takes " + (endTime - startTime)
		        + " milliseconds to create index for the files in directory " + dataDir.getPath());
	}

	/**
	 * 读取txt文件的内容
	 * 
	 * @param file
	 *            想要读取的文件对象
	 * @return 返回文件内容
	 */
	public static String txt2String(File file) {
		String result = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));// 构造一个BufferedReader类来读取文件
			String s = null;
			while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
				result = result + "\n" + s;
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
