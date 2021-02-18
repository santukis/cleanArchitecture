package com.santukis.cleanarchitecture.artwork

import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.artwork.domain.model.Dating

object ArtworkDataProvider {

    val artWorkResponseMultipleItems = "{" +
            "    \"elapsedMilliseconds\": 0," +
            "    \"count\": 3540," +
            "    \"countFacets\": {" +
            "        \"hasimage\": 2924," +
            "        \"ondisplay\": 14" +
            "    }," +
            "    \"artObjects\": [" +
            "        {" +
            "            \"links\": {" +
            "                \"self\": \"http://www.rijksmuseum.nl/api/en/collection/SK-C-597\"," +
            "                \"web\": \"http://www.rijksmuseum.nl/en/collection/SK-C-597\"" +
            "            }," +
            "            \"id\": \"en-SK-C-597\"," +
            "            \"objectNumber\": \"SK-C-597\"," +
            "            \"title\": \"Portrait of a Woman, Possibly Maria Trip\"," +
            "            \"hasImage\": true," +
            "            \"principalOrFirstMaker\": \"Rembrandt van Rijn\"," +
            "            \"longTitle\": \"Portrait of a Woman, Possibly Maria Trip, Rembrandt van Rijn, 1639\"," +
            "            \"showImage\": true," +
            "            \"permitDownload\": true," +
            "            \"webImage\": {" +
            "                \"guid\": \"165d03bb-95e8-4447-a911-865f1bd201d6\"," +
            "                \"offsetPercentageX\": 0," +
            "                \"offsetPercentageY\": 0," +
            "                \"width\": 2031," +
            "                \"height\": 2676," +
            "                \"url\": \"https://lh3.googleusercontent.com/AyiKhdEWJ7XmtPXQbRg_kWqKn6mCV07bsuUB01hJHjVVP-ZQFmzjTWt7JIWiQFZbb9l5tKFhVOspmco4lMwqwWImfgg=s0\"" +
            "            }," +
            "            \"headerImage\": {" +
            "                \"guid\": \"f0270e59-baca-4429-ba00-d6c37b9ad00f\"," +
            "                \"offsetPercentageX\": 0," +
            "                \"offsetPercentageY\": 0," +
            "                \"width\": 1920," +
            "                \"height\": 460," +
            "                \"url\": \"https://lh3.googleusercontent.com/MonorlaCKkTjeFrTBQyCb7kfmq8K--aWrThge7MsTItJxO_cw-8WbwgmTm0jTt-qfaFfnwa1qNEnsGYVHMPmCGmb0Pje=s0\"" +
            "            }," +
            "            \"productionPlaces\": []" +
            "        }," +
            "        {" +
            "            \"links\": {" +
            "                \"self\": \"http://www.rijksmuseum.nl/api/en/collection/SK-C-216\"," +
            "                \"web\": \"http://www.rijksmuseum.nl/en/collection/SK-C-216\"" +
            "            }," +
            "            \"id\": \"en-SK-C-216\"," +
            "            \"objectNumber\": \"SK-C-216\"," +
            "            \"title\": \"Isaac and Rebecca, Known as ‘The Jewish Bride’\"," +
            "            \"hasImage\": true," +
            "            \"principalOrFirstMaker\": \"Rembrandt van Rijn\"," +
            "            \"longTitle\": \"Isaac and Rebecca, Known as ‘The Jewish Bride’, Rembrandt van Rijn, c. 1665 - c. 1669\"," +
            "            \"showImage\": true," +
            "            \"permitDownload\": true," +
            "            \"webImage\": {" +
            "                \"guid\": \"2fe159d5-619a-46d3-b3d2-9412e6f6bdd8\"," +
            "                \"offsetPercentageX\": 0," +
            "                \"offsetPercentageY\": 0," +
            "                \"width\": 2500," +
            "                \"height\": 1817," +
            "                \"url\": \"https://lh3.googleusercontent.com/mAyAjvYjIeAIlByhJx1Huctgeb58y7519XYP38oL1FXarhVlcXW7kxuwayOCFdnwtOp6B6F0HJmmws-Ceo5b_pNSSQs=s0\"" +
            "            }," +
            "            \"headerImage\": {" +
            "                \"guid\": \"10911bfe-4e23-4c3a-95f7-515609f4d711\"," +
            "                \"offsetPercentageX\": 0," +
            "                \"offsetPercentageY\": 0," +
            "                \"width\": 1925," +
            "                \"height\": 461," +
            "                \"url\": \"https://lh3.googleusercontent.com/0fTHP4xUsJZbQjzIHYtgHr1gRWBXjm9udaZT4ah35J_LyNzga-SyI0h61klse9sJqn53eUfuVIWZvP8IhW0jN4v91SM=s0\"" +
            "            }," +
            "            \"productionPlaces\": []" +
            "        }" +
            "    ]" +
            "}"

    val artworkResponseSingleItem = "{" +
            "    \"elapsedMilliseconds\": 154," +
            "    \"artObject\": {" +
            "        \"links\": {" +
            "            \"search\": \"http://www.rijksmuseum.nl/api/nl/collection\"" +
            "        }," +
            "        \"id\": \"en-SK-C-6\"," +
            "        \"priref\": \"5217\"," +
            "        \"objectNumber\": \"SK-C-6\"," +
            "        \"language\": \"en\"," +
            "        \"title\": \"The Sampling Officials of the Amsterdam Drapers’ Guild, Known as ‘The Syndics’\"," +
            "        \"copyrightHolder\": null," +
            "        \"webImage\": {" +
            "            \"guid\": \"2c1ac367-9b11-4266-bb08-eea14ca0bb76\"," +
            "            \"offsetPercentageX\": 50," +
            "            \"offsetPercentageY\": 50," +
            "            \"width\": 3000," +
            "            \"height\": 1975," +
            "            \"url\": \"https://lh3.googleusercontent.com/gShVRyvLLbwVB8jeIPghCXgr96wxTHaM4zqfmxIWRsUpMhMn38PwuUU13o1mXQzLMt5HFqX761u8Tgo4L_JG1XLATvw=s0\"" +
            "        }," +
            "        \"colors\": [" +
            "            {" +
            "                \"percentage\": 1," +
            "                \"hex\": \"#8B7759\"" +
            "            }," +
            "            {" +
            "                \"percentage\": 16," +
            "                \"hex\": \" #432F1C\"" +
            "            }," +
            "            {" +
            "                \"percentage\": 58," +
            "                \"hex\": \" #16130C\"" +
            "            }," +
            "            {" +
            "                \"percentage\": 0," +
            "                \"hex\": \" #B2A387\"" +
            "            }," +
            "            {" +
            "                \"percentage\": 0," +
            "                \"hex\": \" #D5D0BA\"" +
            "            }," +
            "            {" +
            "                \"percentage\": 21," +
            "                \"hex\": \" #745133\"" +
            "            }" +
            "        ]," +
            "        \"colorsWithNormalization\": [" +
            "            {" +
            "                \"originalHex\": \"#8B7759\"," +
            "                \"normalizedHex\": \"#E0CC91\"" +
            "            }," +
            "            {" +
            "                \"originalHex\": \" #432F1C\"," +
            "                \"normalizedHex\": \"#000000\"" +
            "            }," +
            "            {" +
            "                \"originalHex\": \" #16130C\"," +
            "                \"normalizedHex\": \"#000000\"" +
            "            }," +
            "            {" +
            "                \"originalHex\": \" #B2A387\"," +
            "                \"normalizedHex\": \"#E0CC91\"" +
            "            }," +
            "            {" +
            "                \"originalHex\": \" #D5D0BA\"," +
            "                \"normalizedHex\": \"#FBF6E1\"" +
            "            }," +
            "            {" +
            "                \"originalHex\": \" #745133\"," +
            "                \"normalizedHex\": \"#B35A1F\"" +
            "            }" +
            "        ]," +
            "        \"normalizedColors\": [" +
            "            {" +
            "                \"percentage\": 58," +
            "                \"hex\": \"#000000\"" +
            "            }," +
            "            {" +
            "                \"percentage\": 38," +
            "                \"hex\": \" #8B4513\"" +
            "            }," +
            "            {" +
            "                \"percentage\": 2," +
            "                \"hex\": \" #D2B48C\"" +
            "            }," +
            "            {" +
            "                \"percentage\": 0," +
            "                \"hex\": \" #F5F5DC\"" +
            "            }" +
            "        ]," +
            "        \"normalized32Colors\": [" +
            "            {" +
            "                \"percentage\": 75," +
            "                \"hex\": \"#000000\"" +
            "            }," +
            "            {" +
            "                \"percentage\": 21," +
            "                \"hex\": \" #B35A1F\"" +
            "            }," +
            "            {" +
            "                \"percentage\": 2," +
            "                \"hex\": \" #E0CC91\"" +
            "            }," +
            "            {" +
            "                \"percentage\": 0," +
            "                \"hex\": \" #FBF6E1\"" +
            "            }" +
            "        ]," +
            "        \"titles\": [" +
            "            \"The Sampling Officials of the Amsterdam Drapers’ Guild, known as ‘The Syndics’\"," +
            "            \"The syndics: the sampling officials (wardens) of the Amsterdam drapers\"" +
            "        ]," +
            "        \"description\": \"De Staalmeesters: het college van staalmeesters (waardijns) van het Amsterdamse lakenbereidersgilde, bijeen rond een tafel waarop een Perzisch kleed ligt, op tafel het opengeslagen stalenboek. Voorgesteld zijn (van links naar rechts): Jacob van Loon (1595-1674), Volckert Jansz (1605/10-81), Willem van Doeyenburg (ca. 1616-87), de knecht Frans Hendricksz Bel (1629-1701), Aernout van der Mye (ca. 1625-81) en Jochem de Neve (1629-81). Rechts boven de haard een schilderij met een brandend baken.\"," +
            "        \"labelText\": null," +
            "        \"objectTypes\": [" +
            "            \"painting\"" +
            "        ]," +
            "        \"objectCollection\": [" +
            "            \"paintings\"" +
            "        ]," +
            "        \"makers\": []," +
            "        \"principalMakers\": [" +
            "            {" +
            "                \"name\": \"Rembrandt van Rijn\"," +
            "                \"unFixedName\": \"Rijn, Rembrandt van\"," +
            "                \"placeOfBirth\": \"Leiden\"," +
            "                \"dateOfBirth\": \"1606-07-15\"," +
            "                \"dateOfBirthPrecision\": null," +
            "                \"dateOfDeath\": \"1669-10-08\"," +
            "                \"dateOfDeathPrecision\": null," +
            "                \"placeOfDeath\": \"Amsterdam\"," +
            "                \"occupation\": [" +
            "                    \"print maker\"," +
            "                    \"draughtsman\"," +
            "                    \"painter\"" +
            "                ]," +
            "                \"roles\": [" +
            "                    \"painter\"" +
            "                ]," +
            "                \"nationality\": null," +
            "                \"biography\": null," +
            "                \"productionPlaces\": []," +
            "                \"qualification\": null" +
            "            }" +
            "        ]," +
            "        \"plaqueDescriptionDutch\": \"Staalmeesters controleerden de kwaliteit van geverfd laken. Rembrandt portretteerde hen in actie, alsof zij opkijken van hun werk. Eén meester staat op of gaat net zitten, waardoor niet alle hoofden zich op één lijn bevinden. Deze slimme ingreep, de vlotte penseelstreken en de subtiele lichtval maken dit werk tot een van de levendigste groepsportretten uit de 17de eeuw. Het doek werd geschilderd voor de Staalhof, maar verhuisde in 1771 naar het Amsterdamse stadhuis.\"," +
            "        \"plaqueDescriptionEnglish\": \"Samplers checked the quality of dyed cloth. Here Rembrandt shows them at work, distracted for a moment and looking up. One syndic is about to sit, or stand, so not all the heads are at the same level. A clever trick which, with the confident brushwork and subtle use of light, make this one of the liveliest group portraits of the 17th century. Originally painted for the sampling hall (Staalhof), in 1771 it was acquired by Amsterdam’s town hall.\"," +
            "        \"principalMaker\": \"Rembrandt van Rijn\"," +
            "        \"artistRole\": null," +
            "        \"associations\": []," +
            "        \"acquisition\": {" +
            "            \"method\": \"loan\"," +
            "            \"date\": \"1808-01-01T00:00:00\"," +
            "            \"creditLine\": \"On loan from the City of Amsterdam\"" +
            "        }," +
            "        \"exhibitions\": []," +
            "        \"materials\": [" +
            "            \"canvas\"," +
            "            \"oil paint (paint)\"" +
            "        ]," +
            "        \"techniques\": []," +
            "        \"productionPlaces\": []," +
            "        \"dating\": {" +
            "            \"presentingDate\": \"1662\"," +
            "            \"sortingDate\": 1662," +
            "            \"period\": 17," +
            "            \"yearEarly\": 1662," +
            "            \"yearLate\": 1662" +
            "        }," +
            "        \"classification\": {" +
            "            \"iconClassIdentifier\": [" +
            "                \"47E241\"," +
            "                \"41A711\"," +
            "                \"49MM32\"" +
            "            ]," +
            "            \"iconClassDescription\": [" +
            "                \"officers trusted with control of quality\"," +
            "                \"table\"," +
            "                \"book - MM - book open\"" +
            "            ]," +
            "            \"motifs\": []," +
            "            \"events\": []," +
            "            \"periods\": []," +
            "            \"places\": [" +
            "                \"Amsterdam\"" +
            "            ]," +
            "            \"people\": [" +
            "                \"Loon, Jacob van\"," +
            "                \"Jansz, Volckert\"," +
            "                \"Doeyenburg, Willem van\"," +
            "                \"Bel, Frans Hendricksz\"," +
            "                \"Mye, Aernout van der\"," +
            "                \"Neve, Jochem de\"" +
            "            ]," +
            "            \"objectNumbers\": [" +
            "                \"SK-C-6\"" +
            "            ]" +
            "        }," +
            "        \"hasImage\": true," +
            "        \"historicalPersons\": [" +
            "            \"Loon, Jacob van\"," +
            "            \"Jansz, Volckert\"," +
            "            \"Doeyenburg, Willem van\"," +
            "            \"Bel, Frans Hendricksz\"," +
            "            \"Mye, Aernout van der\"," +
            "            \"Neve, Jochem de\"" +
            "        ]," +
            "        \"inscriptions\": []," +
            "        \"documentation\": [" +
            "            \"Tim Zeedijk, ‘Why Carpets are Beautiful’, in Kneeling, 5 years of Making Carpets, Eindhoven (MU Artspace) 2014, afb. p. 10.\"," +
            "            \"N. Middelkoop, 'Kruisbestuiving : Schutters- en regentenstukken in Haarlem en Amsterdam', Kunstschrift (2014) nr. 4, p. 34, afb. 34-35.\"," +
            "            \"The Rembrandt Database,  Object information, Rembrandt,  The sampling officials of the Amsterdam drapers guild,  dated 1662, Rijksmuseum, Amsterdam, inv. no. SK-C-6, http://www.rembrandtdatabase.org/Rembrandt/painting/3046/the-sampling-officials-of-the-amsterdam-drapers-guild, accessed 2016 February 01\"," +
            "            \"B. Alsdorf, La fraternité des individus: les portraits de groupe de Degas, in: La revue de Musée d'Orsay nr. 30 (automne 2010) 48, 14, p. 25, fig. 9\"," +
            "            \"R. Bergmans, Rembrandts kijk op De Staalmeesters, 2009-07- http://mathart.come2me.nl/1014134/REMBRANDT-Staalmeesters [artikel over het perspectief]\"," +
            "            \"Marleen Dominicus-Van Soest, 'Rembrandt in nieuw licht', Rijksmuseum Kunstkrant 19 (1993) nr. 3.\"," +
            "            \"J.G. van Gelder, 'De Staalmeesters', Openbaar Kunstbezit (1964), p. 11a/b.\"," +
            "            \"I.H. van Eeghen, \\\"De Staalmeesters\\\", Jaarboek Amstelodamum 1956, p. 65 - 80\"," +
            "            \"Inzoomer object op zaal, 2013 (Nederlands/English).\"" +
            "        ]," +
            "        \"catRefRPK\": []," +
            "        \"principalOrFirstMaker\": \"Rembrandt van Rijn\"," +
            "        \"dimensions\": [" +
            "            {" +
            "                \"unit\": \"cm\"," +
            "                \"type\": \"height\"," +
            "                \"part\": null," +
            "                \"value\": \"191.5\"" +
            "            }," +
            "            {" +
            "                \"unit\": \"cm\"," +
            "                \"type\": \"width\"," +
            "                \"part\": null," +
            "                \"value\": \"279\"" +
            "            }" +
            "        ]," +
            "        \"physicalProperties\": []," +
            "        \"physicalMedium\": \"oil on canvas\"," +
            "        \"longTitle\": \"The Sampling Officials of the Amsterdam Drapers’ Guild, Known as ‘The Syndics’, Rembrandt van Rijn, 1662\"," +
            "        \"subTitle\": \"h 191.5cm × w 279cm\"," +
            "        \"scLabelLine\": \"oil on canvas, 1662\"," +
            "        \"label\": {" +
            "            \"title\": \"The Sampling Officials of the Amsterdam Drapers’ Guild, Known as ‘The Syndics’\"," +
            "            \"makerLine\": \"oil on canvas, 1662\"," +
            "            \"description\": \"After suffering financial difficulties in the 1650s, Rembrandt moved to a rental house on the Rozengracht. The Amsterdam élite no longer knocked on his door as often as they had done before. He nevertheless remained popular: this important guild commissioned him to paint a group portrait. Rembrandt produced a lively scene by having the wardens look up from their work as if interrupted by our arrival.\"," +
            "            \"notes\": \"text based on label 2019-02-26\"," +
            "            \"date\": \"2020-01-20\"" +
            "        }," +
            "        \"showImage\": true," +
            "        \"location\": \"HG-2.30.7\"" +
            "    }," +
            "    \"artObjectPage\": {" +
            "        \"id\": \"en-SK-C-6\"," +
            "        \"similarPages\": []," +
            "        \"lang\": \"en\"," +
            "        \"objectNumber\": \"SK-C-6\"," +
            "        \"tags\": []," +
            "        \"plaqueDescription\": \"Samplers checked the quality of dyed cloth. Here Rembrandt shows them at work, distracted for a moment and looking up. One syndic is about to sit, or stand, so not all the heads are at the same level. A clever trick which, with the confident brushwork and subtle use of light, make this one of the liveliest group portraits of the 17th century. Originally painted for the sampling hall (Staalhof), in 1771 it was acquired by Amsterdam’s town hall.\"," +
            "        \"audioFile1\": null," +
            "        \"audioFileLabel1\": null," +
            "        \"audioFileLabel2\": null," +
            "        \"createdOn\": \"0001-01-01T00:00:00\"," +
            "        \"updatedOn\": \"2012-09-18T14:06:24.0435196+00:00\"," +
            "        \"adlibOverrides\": {" +
            "            \"titel\": null," +
            "            \"maker\": null," +
            "            \"etiketText\": null" +
            "        }" +
            "    }" +
            "}"

    val artworkResponseSingleItemWithNulls = "{" +
            "    \"elapsedMilliseconds\": 154," +
            "    \"artObject\": {" +
            "        \"links\": {" +
            "            \"search\": \"http://www.rijksmuseum.nl/api/nl/collection\"" +
            "        }," +
            "        \"id\": \"en-SK-C-6\"," +
            "        \"priref\": \"5217\"," +
            "        \"objectNumber\": \"SK-C-6\"," +
            "        \"language\": \"en\"," +
            "        \"title\": null," +
            "        \"copyrightHolder\": null," +
            "        \"webImage\": {" +
            "            \"guid\": \"2c1ac367-9b11-4266-bb08-eea14ca0bb76\"," +
            "            \"offsetPercentageX\": 50," +
            "            \"offsetPercentageY\": 50," +
            "            \"width\": 3000," +
            "            \"height\": 1975," +
            "            \"url\": null" +
            "        }," +
            "        \"colors\": [" +
            "            {" +
            "                \"percentage\": null," +
            "                \"hex\": null" +
            "            }" +
            "        ]," +
            "        \"colorsWithNormalization\": []," +
            "        \"normalizedColors\": []," +
            "        \"normalized32Colors\": [" +
            "            {" +
            "                \"percentage\": null," +
            "                \"hex\": null" +
            "            }" +
            "        ]," +
            "        \"plaqueDescriptionEnglish\": null," +
            "        \"dating\": {" +
            "            \"presentingDate\": null," +
            "            \"sortingDate\": null," +
            "            \"period\": 17," +
            "            \"yearEarly\": null," +
            "            \"yearLate\": null" +
            "        }," +
            "        \"principalOrFirstMaker\": null," +
            "        \"dimensions\": null" +
            "    }" +
            "}"

    val artworks = listOf(
        Artwork(
            id = "1",
            title = "title01",
            description = "description01",
            author = "author01",
            dating = Dating(year = 1111),
            image = "image01"
        ),
        Artwork(
            id = "2",
            title = "title02",
            description = "description02",
            author = "author02",
            dating = Dating(year = 1111),
            image = "image02"
        ),
        Artwork(
            id = "3",
            title = "title03",
            description = "description03",
            author = "author03",
            dating = Dating(year = 1111),
            image = "image03"
        )
    )
}