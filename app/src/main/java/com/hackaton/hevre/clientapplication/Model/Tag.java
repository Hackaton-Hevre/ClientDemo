package com.hackaton.hevre.clientapplication.Model;

/**
 * Created by אביחי on 23/10/2016.
 */
public enum Tag {

    TAG_RESTURANTS 							 ("RESTURANTS"),
    TAG_SPORT 								 ("SPORT"),
    TAG_MUSIC 								 ("MUSIC"),
    TAG_ELECTRONIC 							 ("ELECTRONIC"),
    TAG_PETS 								 ("PETS"),
    TAG_PHARM 								 ("PHARM"),
    TAG_OFFICE_SUPPLY 						 ("OFFICE_SUPPLY"),
    TAG_ACCOUNTING                           ("ACCOUNTING"),
    TAG_AIRPORT                              ("AIRPORT"),
    TAG_AMUSEMENT_PARK                       ("AMUSEMENT_PARK"),
    TAG_AQUARIUM                             ("AQUARIUM"),
    TAG_ART_GALLERY                          ("ART_GALLERY"),
    TAG_ATM                                  ("ATM"),
    TAG_BAKERY                               ("BAKERY"),
    TAG_BANK                                 ("BANK"),
    TAG_BAR                                  ("BAR"),
    TAG_BEAUTY_SALON                         ("BEAUTY_SALON"),
    TAG_BICYCLE_STORE                        ("BICYCLE_STORE"),
    TAG_BOOK_STORE                           ("BOOK_STORE"),
    TAG_BOWLING_ALLEY                        ("BOWLING_ALLEY"),
    TAG_BUS_STATION                          ("BUS_STATION"),
    TAG_CAFE                                 ("CAFE"),
    TAG_CAMPGROUND                           ("CAMPGROUND"),
    TAG_CAR_DEALER                           ("CAR_DEALER"),
    TAG_CAR_RENTAL                           ("CAR_RENTAL"),
    TAG_CAR_REPAIR                           ("CAR_REPAIR"),
    TAG_CAR_WASH                             ("CAR_WASH"),
    TAG_CASINO                               ("CASINO"),
    TAG_CEMETERY                             ("CEMETERY"),
    TAG_CHURCH                               ("CHURCH"),
    TAG_CITY_HALL                            ("CITY_HALL"),
    TAG_CLOTHING_STORE                       ("CLOTHING_STORE"),
    TAG_CONVENIENCE_STORE                    ("CONVENIENCE_STORE"),
    TAG_COURTHOUSE                           ("COURTHOUSE"),
    TAG_DENTIST                              ("DENTIST"),
    TAG_DEPARTMENT_STORE                     ("DEPARTMENT_STORE"),
    TAG_DOCTOR                               ("DOCTOR"),
    TAG_ELECTRICIAN                          ("ELECTRICIAN"),
    TAG_ELECTRONICS_STORE                    ("ELECTRONICS_STORE"),
    TAG_EMBASSY                              ("EMBASSY"),
    TAG_ESTABLISHMENT                        ("ESTABLISHMENT"),
    TAG_FINANCE                              ("FINANCE"),
    TAG_FIRE_STATION                         ("FIRE_STATION"),
    TAG_FLORIST                              ("FLORIST"),
    TAG_FOOD                                 ("FOOD"),
    TAG_FUNERAL_HOME                         ("FUNERAL_HOME"),
    TAG_FURNITURE_STORE                      ("FURNITURE_STORE"),
    TAG_GAS_STATION                          ("GAS_STATION"),
    TAG_GENERAL_CONTRACTOR                   ("GENERAL_CONTRACTOR"),
    TAG_GROCERY_OR_SUPERMARKET               ("GROCERY_OR_SUPERMARKET"),
    TAG_GYM                                  ("GYM"),
    TAG_HAIR_CARE                            ("HAIR_CARE"),
    TAG_HARDWARE_STORE                       ("HARDWARE_STORE"),
    TAG_HEALTH                               ("HEALTH"),
    TAG_HINDU_TEMPLE                         ("HINDU_TEMPLE"),
    TAG_HOME_GOODS_STORE                     ("HOME_GOODS_STORE"),
    TAG_HOSPITAL                             ("HOSPITAL"),
    TAG_INSURANCE_AGENCY                     ("INSURANCE_AGENCY"),
    TAG_JEWELRY_STORE                        ("JEWELRY_STORE"),
    TAG_LAUNDRY                              ("LAUNDRY"),
    TAG_LAWYER                               ("LAWYER"),
    TAG_LIBRARY                              ("LIBRARY"),
    TAG_LIQUOR_STORE                         ("LIQUOR_STORE"),
    TAG_LOCAL_GOVERNMENT_OFFICE              ("LOCAL_GOVERNMENT_OFFICE"),
    TAG_LOCKSMITH                            ("LOCKSMITH"),
    TAG_LODGING                              ("LODGING"),
    TAG_MEAL_DELIVERY                        ("MEAL_DELIVERY"),
    TAG_MEAL_TAKEAWAY                        ("MEAL_TAKEAWAY"),
    TAG_MOSQUE                               ("MOSQUE"),
    TAG_MOVIE_RENTAL                         ("MOVIE_RENTAL"),
    TAG_MOVIE_THEATER                        ("MOVIE_THEATER"),
    TAG_MOVING_COMPANY                       ("MOVING_COMPANY"),
    TAG_MUSEUM                               ("MUSEUM"),
    TAG_NIGHT_CLUB                           ("NIGHT_CLUB"),
    TAG_PAINTER                              ("PAINTER"),
    TAG_PARK                                 ("PARK"),
    TAG_PARKING                              ("PARKING"),
    TAG_PET_STORE                            ("PET_STORE"),
    TAG_PHARMACY                             ("PHARMACY"),
    TAG_PHYSIOTHERAPIST                      ("PHYSIOTHERAPIST"),
    TAG_PLACE_OF_WORSHIP                     ("PLACE_OF_WORSHIP"),
    TAG_PLUMBER                              ("PLUMBER"),
    TAG_POLICE                               ("POLICE"),
    TAG_POST_OFFICE                          ("POST_OFFICE"),
    TAG_REAL_ESTATE_AGENCY                   ("REAL_ESTATE_AGENCY"),
    TAG_RESTAURANT                           ("RESTAURANT"),
    TAG_ROOFING_CONTRACTOR                   ("ROOFING_CONTRACTOR"),
    TAG_RV_PARK                              ("RV_PARK"),
    TAG_SCHOOL                               ("SCHOOL"),
    TAG_SHOE_STORE                           ("SHOE_STORE"),
    TAG_SHOPPING_MALL                        ("SHOPPING_MALL"),
    TAG_SPA                                  ("SPA"),
    TAG_STADIUM                              ("STADIUM"),
    TAG_STORAGE                              ("STORAGE"),
    TAG_STORE                                ("STORE"),
    TAG_SUBWAY_STATION                       ("SUBWAY_STATION"),
    TAG_SYNAGOGUE                            ("SYNAGOGUE"),
    TAG_TAXI_STAND                           ("TAXI_STAND"),
    TAG_TRAIN_STATION                        ("TRAIN_STATION"),
    TAG_TRANSIT_STATION                      ("TRANSIT_STATION"),
    TAG_TRAVEL_AGENCY                        ("TRAVEL_AGENCY"),
    TAG_UNIVERSITY                           ("UNIVERSITY"),
    TAG_VETERINARY_CARE                      ("VETERINARY_CARE"),
    TAG_ZOO                                  ("ZOO");


    private final String name;

    Tag(String name) {
        this.name = name.toLowerCase();
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }

}