/*Copyright (c) 2016 "hbz"

This file is part of zettel.

etikett is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package services;

import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvMapReader;
import org.supercsv.io.ICsvMapReader;
import org.supercsv.prefs.CsvPreference;

/**
 * @author Jan Schnasse
 *
 */

@SuppressWarnings("javadoc")
public class ResearchDataHelper {
	
	public static Map<String, String> ddc = readCsv("ddcEN.csv");
	
	private static CellProcessor[] getProcessors() {
		final CellProcessor[] processors = new CellProcessor[] { new NotNull(), // URI
				new NotNull(), // Label
		};
		return processors;
	}
	
	private static Map<String, String> readCsv(String resource) {
		String path = play.Play.application().resource(resource).getPath();
		play.Logger.info("Read " + resource + " from " + path);
		Map<String, String> result = new LinkedHashMap<>();
		try (ICsvMapReader reader = new CsvMapReader(new FileReader(path),
				new CsvPreference.Builder('"', ',', "\n").build());) {
			String[] header = reader.getHeader(true);
			CellProcessor[] processors = getProcessors();
			Map<String, Object> rec;
			while ((rec = reader.read(header, processors)) != null) {
				result.put(rec.get("URI").toString(), rec.get("Label").toString());
			}
			play.Logger.info(result + "");
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @return a map that can be used in an html select
	 */
	public static LinkedHashMap<String, String> getDeweyMap() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put(null, "Bitte wählen Sie...");

		map.put("http://dewey.info/class/020/",
				"020 Bibliotheks- und Informationswissenschaft");
		map.put("http://dewey.info/class/150/", "150 Psychologie");
		map.put("http://dewey.info/class/300/",
				"300 Sozialwissenschaften,Soziologie, Athropologie");
		map.put("http://dewey.info/class/320/", "320 Politik");
		map.put("http://dewey.info/class/330/", "330 Wirtschaft");
		map.put("http://dewey.info/class/333/7/",
				"333.7 Natürliche Ressourcen,Energie & Umwelt");
		map.put("http://dewey.info/class/340/", "340 Recht");
		map.put("http://dewey.info/class/360/",
				"360 Soziale Probleme, Sozialdienste, Versicherungen");
		map.put("http://dewey.info/class/500/", "500 Naturwissenschaften");
		map.put("http://dewey.info/class/530/", "530 Physik");
		map.put("http://dewey.info/class/540/", "540 Chemie");
		map.put("http://dewey.info/class/550/", "550 Geowissenschaften");
		map.put("http://dewey.info/class/560/", "560 Fossilien/Paläontologie");
		map.put("http://dewey.info/class/570/", "570 Biowissenschaften, Biologie");
		map.put("http://dewey.info/class/580/", "580 Pflanzen (Botanik)");
		map.put("http://dewey.info/class/590/", "590 Tiere (Zoologie)");
		map.put("http://dewey.info/class/600/", "600 Technik");
		map.put("http://dewey.info/class/610/", "610 Medizin & Gesundheit");
		map.put("http://dewey.info/class/624/",
				"624 Ingenieurbau und Umwelttechnik");
		map.put("http://dewey.info/class/630/",
				"630 Landwirtschaft, Veterinärmedizin");
		map.put("http://dewey.info/class/640/", "640 Hauswirtschaft/Familie");
		map.put("http://dewey.info/class/650/", "650 Management");
		map.put("http://dewey.info/class/660/", "660 Chemische Verfahrenstechnik");
		map.put("http://dewey.info/class/710/", "710 Landschaftsgestaltung");
		map.put("http://dewey.info/class/720/", "720 Architektur");
		map.put("http://dewey.info/class/940/", "940 Geschichte Europas");
		map.put("http://dewey.info/class/943/", "943 Geschichte Deutschlands");
		return map;
	}

	/**
	 * @return a map that can be used in an html select
	 */
	public static LinkedHashMap<String, String> getProfessionalGroupMap() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put(null, "Bitte wählen Sie...");
		map.put("http://skos.um.es/unescothes/C02464", "Medizin");
		map.put("http://skos.um.es/unescothes/C01771", "Gesundheitswesen");
		map.put("http://skos.um.es/unescothes/C02780", "Ernährungswissenschaften");
		map.put("http://skos.um.es/unescothes/C00106", "Agrarwissenschaften");
		map.put("http://skos.um.es/unescothes/C01397", "Umweltwissenschaften");
		map.put("http://skos.um.es/unescothes/COL270", "Biologie");
		map.put("http://skos.um.es/unescothes/C02286",
				"Bibliotheks- und Informationswissenschaften");
		map.put("http://skos.um.es/unescothes/C02053", "Interdisziplinär");
		return map;
	}

	/**
	 * @return a map that can be used in an html select
	 */
	public static LinkedHashMap<String, String> getDataOriginMap() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put(null, "Please select...");
		map.put("http://hbz-nrw.de/regal#Ausbreitungsmodellierung", "Air dispersion modelling");
		map.put("http://hbz-nrw.de/regal#Analyse", "Analysis");
		map.put("http://hbz-nrw.de/regal#Anamnese", "Anamnesis");
		map.put("http://hbz-nrw.de/regal#Bodenbohrung", "Borehole");
		map.put("http://hbz-nrw.de/regal#Berechnung", "Calculation");
		map.put("http://hbz-nrw.de/regal#Kohortenstudie", "Cohort study");
		map.put("http://hbz-nrw.de/regal#Querschnittstudie", "Cross-sectional study");
		map.put("http://hbz-nrw.de/regal#Evaluation", "Evaluation");
		map.put("http://hbz-nrw.de/regal#Exploration", "Exploration");
		map.put("http://hbz-nrw.de/regal#Feldbeobachtung", "Field observation");
		map.put("http://hbz-nrw.de/regal#Genomsequenzierung", "Genome sequencing");
		map.put("http://hbz-nrw.de/regal#apparativeUntersuchung",
				"Instrumental examination");
		map.put("http://hbz-nrw.de/regal#Interventionsstudie",
				"Interventional study");
		map.put("http://hbz-nrw.de/regal#Interview", "Interview");
		map.put("http://hbz-nrw.de/regal#Laborbeobachtung", "Laboratory observation");
		map.put("http://hbz-nrw.de/regal#Langzeitstudie", "Long-term study");
		map.put("http://hbz-nrw.de/regal#Messung", "Measurement");
		map.put("http://hbz-nrw.de/regal#MessungExSitu", "Measurement ex situ");
		map.put("http://hbz-nrw.de/regal#MessungInSitu", "Measurement in situ");
		map.put("http://hbz-nrw.de/regal#Andere", "Other");
		map.put("http://hbz-nrw.de/regal#koerperlicheUntersuchung",
				"Physical examination");
		map.put("http://hbz-nrw.de/regal#Quelltermrückrechnung", "Reverse dispersion modelling");
		map.put("http://hbz-nrw.de/regal#Probe", "Sample");
		map.put("http://hbz-nrw.de/regal#Simulation", "Simulation");
		map.put("http://hbz-nrw.de/regal#Flaechenmischprobe", "Surface composite sample");
		map.put("http://hbz-nrw.de/regal#Umfrage", "Survey");
		map.put("http://hbz-nrw.de/regal#Gewebeprobe", "Tissue sample");
		return map;
	}

	/**
	 * @return a map that can be used in an html select
	 */
	public static LinkedHashMap<String, String> getMediumMap() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put(null, "Please select...");
		map.put("http://purl.org/ontology/bibo/AudioDocument", "Audio");
		map.put("http://purl.org/dc/dcmitype/Dataset", "Dataset");
		map.put("http://purl.org/ontology/bibo/Image", "Image");
		map.put("http://purl.org/lobid/lv#Miscellaneous", "Miscellaneous");
		map.put("http://pbcore.org/vocabularies/instantiationMediaType#software",
				"Software");
		map.put("http://rdvocab.info/termList/RDACarrierType/1050", "Video");
		map.put("http://pbcore.org/vocabularies/instantiationMediaType#text",
				"Text");
		return map;
	}

	/**
	 * @return a map that can be used in an html select
	 */
	public static LinkedHashMap<String, String> getLicenseMap() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("https://creativecommons.org/licenses/by/4.0", "CC BY 4.0 (Recommended)");
		map.put("https://creativecommons.org/publicdomain/zero/1.0/", "CC0 1.0");
		map.put("http://opendatacommons.org/licenses/odbl/1-0/",
				"ODbL (Open Database License)");
		map.put("http://www.gnu.org/licenses/gpl-3.0.de.html",
				"GNU GPL (GNU General Public Licence)");
		map.put("https://opensource.org/licenses/MIT",
				"MIT Licence");
		return map;
	}

	/**
	 * @return a map that can be used in an html select
	 */
	public static LinkedHashMap<String, String> getLanguageMap() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put(null, "Please select...");
		map.put("http://id.loc.gov/vocabulary/iso639-2/eng", "English");
		map.put("http://id.loc.gov/vocabulary/iso639-2/fra", "French");
		map.put("http://id.loc.gov/vocabulary/iso639-2/ger", "German");
		map.put("http://id.loc.gov/vocabulary/iso639-2/ita", "Italian");
		map.put("http://id.loc.gov/vocabulary/iso639-2/spa", "Spanish");
		return map;
	}

	/**
	 * @return a map of yyyy strings with the last hundred years descending from
	 *         today
	 */
	public static LinkedHashMap<String, String> getCopyrightYear() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		List<LocalDate> years =
				Stream.iterate(LocalDate.now(), date -> date.minusYears(1)).limit(100)
						.collect(Collectors.toList());
		for (LocalDate d : years) {
			String ds = d.format(DateTimeFormatter.ofPattern("yyyy"));
			map.put(ds, ds);
		}

		return map;
	}

	public static LinkedHashMap<String, String> getPersonLookupEndpoints() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("https://lobid.org/person", "GND");
		map.put("/tools/zettel/orcidAutocomplete", "ORCID");
		return map;
	}

	public static LinkedHashMap<String, String> getSubjectLookupEndpoints() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("https://lobid.org/subject", "GND");
		map.put("/tools/skos-lookup/autocomplete", "Agrovoc");
		return map;
	}

	public static LinkedHashMap<String, String> getEmptyLookupEndpoints() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		return map;
	}
}