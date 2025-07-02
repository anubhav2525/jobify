export interface CountryCode {
  code: string;
  label: string;
  dialingCode: string;
}

export const countryCodes: readonly CountryCode[] = [
  // A
  { code: "AD", label: "Andorra", dialingCode: "+376" },
  { code: "AE", label: "United Arab Emirates", dialingCode: "+971" },
  { code: "AF", label: "Afghanistan", dialingCode: "+93" },
  { code: "AG", label: "Antigua and Barbuda", dialingCode: "+1268" },
  { code: "AI", label: "Anguilla", dialingCode: "+1264" },
  { code: "AL", label: "Albania", dialingCode: "+355" },
  { code: "AM", label: "Armenia", dialingCode: "+374" },
  { code: "AO", label: "Angola", dialingCode: "+244" },
  { code: "AQ", label: "Antarctica", dialingCode: "+672" },
  { code: "AR", label: "Argentina", dialingCode: "+54" },
  { code: "AS", label: "American Samoa", dialingCode: "+1684" },
  { code: "AT", label: "Austria", dialingCode: "+43" },
  { code: "AU", label: "Australia", dialingCode: "+61" },
  { code: "AW", label: "Aruba", dialingCode: "+297" },
  { code: "AX", label: "Åland Islands", dialingCode: "+358" },
  { code: "AZ", label: "Azerbaijan", dialingCode: "+994" },

  // B
  { code: "BA", label: "Bosnia and Herzegovina", dialingCode: "+387" },
  { code: "BB", label: "Barbados", dialingCode: "+1246" },
  { code: "BD", label: "Bangladesh", dialingCode: "+880" },
  { code: "BE", label: "Belgium", dialingCode: "+32" },
  { code: "BF", label: "Burkina Faso", dialingCode: "+226" },
  { code: "BG", label: "Bulgaria", dialingCode: "+359" },
  { code: "BH", label: "Bahrain", dialingCode: "+973" },
  { code: "BI", label: "Burundi", dialingCode: "+257" },
  { code: "BJ", label: "Benin", dialingCode: "+229" },
  { code: "BL", label: "Saint Barthélemy", dialingCode: "+590" },
  { code: "BM", label: "Bermuda", dialingCode: "+1441" },
  { code: "BN", label: "Brunei", dialingCode: "+673" },
  { code: "BO", label: "Bolivia", dialingCode: "+591" },
  { code: "BQ", label: "Caribbean Netherlands", dialingCode: "+599" },
  { code: "BR", label: "Brazil", dialingCode: "+55" },
  { code: "BS", label: "Bahamas", dialingCode: "+1242" },
  { code: "BT", label: "Bhutan", dialingCode: "+975" },
  { code: "BV", label: "Bouvet Island", dialingCode: "+47" },
  { code: "BW", label: "Botswana", dialingCode: "+267" },
  { code: "BY", label: "Belarus", dialingCode: "+375" },
  { code: "BZ", label: "Belize", dialingCode: "+501" },

  // C
  { code: "CA", label: "Canada", dialingCode: "+1" },
  { code: "CC", label: "Cocos Islands", dialingCode: "+61" },
  {
    code: "CD",
    label: "Democratic Republic of the Congo",
    dialingCode: "+243",
  },
  { code: "CF", label: "Central African Republic", dialingCode: "+236" },
  { code: "CG", label: "Republic of the Congo", dialingCode: "+242" },
  { code: "CH", label: "Switzerland", dialingCode: "+41" },
  { code: "CI", label: "Côte d'Ivoire", dialingCode: "+225" },
  { code: "CK", label: "Cook Islands", dialingCode: "+682" },
  { code: "CL", label: "Chile", dialingCode: "+56" },
  { code: "CM", label: "Cameroon", dialingCode: "+237" },
  { code: "CN", label: "China", dialingCode: "+86" },
  { code: "CO", label: "Colombia", dialingCode: "+57" },
  { code: "CR", label: "Costa Rica", dialingCode: "+506" },
  { code: "CU", label: "Cuba", dialingCode: "+53" },
  { code: "CV", label: "Cape Verde", dialingCode: "+238" },
  { code: "CW", label: "Curaçao", dialingCode: "+599" },
  { code: "CX", label: "Christmas Island", dialingCode: "+61" },
  { code: "CY", label: "Cyprus", dialingCode: "+357" },
  { code: "CZ", label: "Czech Republic", dialingCode: "+420" },

  // D
  { code: "DE", label: "Germany", dialingCode: "+49" },
  { code: "DJ", label: "Djibouti", dialingCode: "+253" },
  { code: "DK", label: "Denmark", dialingCode: "+45" },
  { code: "DM", label: "Dominica", dialingCode: "+1767" },
  { code: "DO", label: "Dominican Republic", dialingCode: "+1" },
  { code: "DZ", label: "Algeria", dialingCode: "+213" },

  // E
  { code: "EC", label: "Ecuador", dialingCode: "+593" },
  { code: "EE", label: "Estonia", dialingCode: "+372" },
  { code: "EG", label: "Egypt", dialingCode: "+20" },
  { code: "EH", label: "Western Sahara", dialingCode: "+212" },
  { code: "ER", label: "Eritrea", dialingCode: "+291" },
  { code: "ES", label: "Spain", dialingCode: "+34" },
  { code: "ET", label: "Ethiopia", dialingCode: "+251" },

  // F
  { code: "FI", label: "Finland", dialingCode: "+358" },
  { code: "FJ", label: "Fiji", dialingCode: "+679" },
  { code: "FK", label: "Falkland Islands", dialingCode: "+500" },
  { code: "FM", label: "Micronesia", dialingCode: "+691" },
  { code: "FO", label: "Faroe Islands", dialingCode: "+298" },
  { code: "FR", label: "France", dialingCode: "+33" },

  // G
  { code: "GA", label: "Gabon", dialingCode: "+241" },
  { code: "GB", label: "United Kingdom", dialingCode: "+44" },
  { code: "GD", label: "Grenada", dialingCode: "+1473" },
  { code: "GE", label: "Georgia", dialingCode: "+995" },
  { code: "GF", label: "French Guiana", dialingCode: "+594" },
  { code: "GG", label: "Guernsey", dialingCode: "+44" },
  { code: "GH", label: "Ghana", dialingCode: "+233" },
  { code: "GI", label: "Gibraltar", dialingCode: "+350" },
  { code: "GL", label: "Greenland", dialingCode: "+299" },
  { code: "GM", label: "Gambia", dialingCode: "+220" },
  { code: "GN", label: "Guinea", dialingCode: "+224" },
  { code: "GP", label: "Guadeloupe", dialingCode: "+590" },
  { code: "GQ", label: "Equatorial Guinea", dialingCode: "+240" },
  { code: "GR", label: "Greece", dialingCode: "+30" },
  {
    code: "GS",
    label: "South Georgia and the South Sandwich Islands",
    dialingCode: "+500",
  },
  { code: "GT", label: "Guatemala", dialingCode: "+502" },
  { code: "GU", label: "Guam", dialingCode: "+1671" },
  { code: "GW", label: "Guinea-Bissau", dialingCode: "+245" },
  { code: "GY", label: "Guyana", dialingCode: "+592" },

  // H
  { code: "HK", label: "Hong Kong", dialingCode: "+852" },
  {
    code: "HM",
    label: "Heard Island and McDonald Islands",
    dialingCode: "+672",
  },
  { code: "HN", label: "Honduras", dialingCode: "+504" },
  { code: "HR", label: "Croatia", dialingCode: "+385" },
  { code: "HT", label: "Haiti", dialingCode: "+509" },
  { code: "HU", label: "Hungary", dialingCode: "+36" },

  // I
  { code: "ID", label: "Indonesia", dialingCode: "+62" },
  { code: "IE", label: "Ireland", dialingCode: "+353" },
  { code: "IL", label: "Israel", dialingCode: "+972" },
  { code: "IM", label: "Isle of Man", dialingCode: "+44" },
  { code: "IN", label: "India", dialingCode: "+91" },
  { code: "IO", label: "British Indian Ocean Territory", dialingCode: "+246" },
  { code: "IQ", label: "Iraq", dialingCode: "+964" },
  { code: "IR", label: "Iran", dialingCode: "+98" },
  { code: "IS", label: "Iceland", dialingCode: "+354" },
  { code: "IT", label: "Italy", dialingCode: "+39" },

  // J
  { code: "JE", label: "Jersey", dialingCode: "+44" },
  { code: "JM", label: "Jamaica", dialingCode: "+1876" },
  { code: "JO", label: "Jordan", dialingCode: "+962" },
  { code: "JP", label: "Japan", dialingCode: "+81" },

  // K
  { code: "KE", label: "Kenya", dialingCode: "+254" },
  { code: "KG", label: "Kyrgyzstan", dialingCode: "+996" },
  { code: "KH", label: "Cambodia", dialingCode: "+855" },
  { code: "KI", label: "Kiribati", dialingCode: "+686" },
  { code: "KM", label: "Comoros", dialingCode: "+269" },
  { code: "KN", label: "Saint Kitts and Nevis", dialingCode: "+1869" },
  { code: "KP", label: "North Korea", dialingCode: "+850" },
  { code: "KR", label: "South Korea", dialingCode: "+82" },
  { code: "KW", label: "Kuwait", dialingCode: "+965" },
  { code: "KY", label: "Cayman Islands", dialingCode: "+1345" },
  { code: "KZ", label: "Kazakhstan", dialingCode: "+7" },

  // L
  { code: "LA", label: "Laos", dialingCode: "+856" },
  { code: "LB", label: "Lebanon", dialingCode: "+961" },
  { code: "LC", label: "Saint Lucia", dialingCode: "+1758" },
  { code: "LI", label: "Liechtenstein", dialingCode: "+423" },
  { code: "LK", label: "Sri Lanka", dialingCode: "+94" },
  { code: "LR", label: "Liberia", dialingCode: "+231" },
  { code: "LS", label: "Lesotho", dialingCode: "+266" },
  { code: "LT", label: "Lithuania", dialingCode: "+370" },
  { code: "LU", label: "Luxembourg", dialingCode: "+352" },
  { code: "LV", label: "Latvia", dialingCode: "+371" },
  { code: "LY", label: "Libya", dialingCode: "+218" },

  // M
  { code: "MA", label: "Morocco", dialingCode: "+212" },
  { code: "MC", label: "Monaco", dialingCode: "+377" },
  { code: "MD", label: "Moldova", dialingCode: "+373" },
  { code: "ME", label: "Montenegro", dialingCode: "+382" },
  { code: "MF", label: "Saint Martin", dialingCode: "+590" },
  { code: "MG", label: "Madagascar", dialingCode: "+261" },
  { code: "MH", label: "Marshall Islands", dialingCode: "+692" },
  { code: "MK", label: "North Macedonia", dialingCode: "+389" },
  { code: "ML", label: "Mali", dialingCode: "+223" },
  { code: "MM", label: "Myanmar", dialingCode: "+95" },
  { code: "MN", label: "Mongolia", dialingCode: "+976" },
  { code: "MO", label: "Macao", dialingCode: "+853" },
  { code: "MP", label: "Northern Mariana Islands", dialingCode: "+1670" },
  { code: "MQ", label: "Martinique", dialingCode: "+596" },
  { code: "MR", label: "Mauritania", dialingCode: "+222" },
  { code: "MS", label: "Montserrat", dialingCode: "+1664" },
  { code: "MT", label: "Malta", dialingCode: "+356" },
  { code: "MU", label: "Mauritius", dialingCode: "+230" },
  { code: "MV", label: "Maldives", dialingCode: "+960" },
  { code: "MW", label: "Malawi", dialingCode: "+265" },
  { code: "MX", label: "Mexico", dialingCode: "+52" },
  { code: "MY", label: "Malaysia", dialingCode: "+60" },
  { code: "MZ", label: "Mozambique", dialingCode: "+258" },

  // N
  { code: "NA", label: "Namibia", dialingCode: "+264" },
  { code: "NC", label: "New Caledonia", dialingCode: "+687" },
  { code: "NE", label: "Niger", dialingCode: "+227" },
  { code: "NF", label: "Norfolk Island", dialingCode: "+672" },
  { code: "NG", label: "Nigeria", dialingCode: "+234" },
  { code: "NI", label: "Nicaragua", dialingCode: "+505" },
  { code: "NL", label: "Netherlands", dialingCode: "+31" },
  { code: "NO", label: "Norway", dialingCode: "+47" },
  { code: "NP", label: "Nepal", dialingCode: "+977" },
  { code: "NR", label: "Nauru", dialingCode: "+674" },
  { code: "NU", label: "Niue", dialingCode: "+683" },
  { code: "NZ", label: "New Zealand", dialingCode: "+64" },

  // O
  { code: "OM", label: "Oman", dialingCode: "+968" },

  // P
  { code: "PA", label: "Panama", dialingCode: "+507" },
  { code: "PE", label: "Peru", dialingCode: "+51" },
  { code: "PF", label: "French Polynesia", dialingCode: "+689" },
  { code: "PG", label: "Papua New Guinea", dialingCode: "+675" },
  { code: "PH", label: "Philippines", dialingCode: "+63" },
  { code: "PK", label: "Pakistan", dialingCode: "+92" },
  { code: "PL", label: "Poland", dialingCode: "+48" },
  { code: "PM", label: "Saint Pierre and Miquelon", dialingCode: "+508" },
  { code: "PN", label: "Pitcairn", dialingCode: "+64" },
  { code: "PR", label: "Puerto Rico", dialingCode: "+1" },
  { code: "PS", label: "Palestine", dialingCode: "+970" },
  { code: "PT", label: "Portugal", dialingCode: "+351" },
  { code: "PW", label: "Palau", dialingCode: "+680" },
  { code: "PY", label: "Paraguay", dialingCode: "+595" },

  // Q
  { code: "QA", label: "Qatar", dialingCode: "+974" },

  // R
  { code: "RE", label: "Réunion", dialingCode: "+262" },
  { code: "RO", label: "Romania", dialingCode: "+40" },
  { code: "RS", label: "Serbia", dialingCode: "+381" },
  { code: "RU", label: "Russia", dialingCode: "+7" },
  { code: "RW", label: "Rwanda", dialingCode: "+250" },

  // S
  { code: "SA", label: "Saudi Arabia", dialingCode: "+966" },
  { code: "SB", label: "Solomon Islands", dialingCode: "+677" },
  { code: "SC", label: "Seychelles", dialingCode: "+248" },
  { code: "SD", label: "Sudan", dialingCode: "+249" },
  { code: "SE", label: "Sweden", dialingCode: "+46" },
  { code: "SG", label: "Singapore", dialingCode: "+65" },
  { code: "SH", label: "Saint Helena", dialingCode: "+290" },
  { code: "SI", label: "Slovenia", dialingCode: "+386" },
  { code: "SJ", label: "Svalbard and Jan Mayen", dialingCode: "+47" },
  { code: "SK", label: "Slovakia", dialingCode: "+421" },
  { code: "SL", label: "Sierra Leone", dialingCode: "+232" },
  { code: "SM", label: "San Marino", dialingCode: "+378" },
  { code: "SN", label: "Senegal", dialingCode: "+221" },
  { code: "SO", label: "Somalia", dialingCode: "+252" },
  { code: "SR", label: "Suriname", dialingCode: "+597" },
  { code: "SS", label: "South Sudan", dialingCode: "+211" },
  { code: "ST", label: "São Tomé and Príncipe", dialingCode: "+239" },
  { code: "SV", label: "El Salvador", dialingCode: "+503" },
  { code: "SX", label: "Sint Maarten", dialingCode: "+1721" },
  { code: "SY", label: "Syria", dialingCode: "+963" },
  { code: "SZ", label: "Eswatini", dialingCode: "+268" },

  // T
  { code: "TC", label: "Turks and Caicos Islands", dialingCode: "+1649" },
  { code: "TD", label: "Chad", dialingCode: "+235" },
  { code: "TF", label: "French Southern Territories", dialingCode: "+262" },
  { code: "TG", label: "Togo", dialingCode: "+228" },
  { code: "TH", label: "Thailand", dialingCode: "+66" },
  { code: "TJ", label: "Tajikistan", dialingCode: "+992" },
  { code: "TK", label: "Tokelau", dialingCode: "+690" },
  { code: "TL", label: "Timor-Leste", dialingCode: "+670" },
  { code: "TM", label: "Turkmenistan", dialingCode: "+993" },
  { code: "TN", label: "Tunisia", dialingCode: "+216" },
  { code: "TO", label: "Tonga", dialingCode: "+676" },
  { code: "TR", label: "Turkey", dialingCode: "+90" },
  { code: "TT", label: "Trinidad and Tobago", dialingCode: "+1868" },
  { code: "TV", label: "Tuvalu", dialingCode: "+688" },
  { code: "TW", label: "Taiwan", dialingCode: "+886" },
  { code: "TZ", label: "Tanzania", dialingCode: "+255" },

  // U
  { code: "UA", label: "Ukraine", dialingCode: "+380" },
  { code: "UG", label: "Uganda", dialingCode: "+256" },
  {
    code: "UM",
    label: "United States Minor Outlying Islands",
    dialingCode: "+1",
  },
  { code: "US", label: "United States", dialingCode: "+1" },
  { code: "UY", label: "Uruguay", dialingCode: "+598" },
  { code: "UZ", label: "Uzbekistan", dialingCode: "+998" },

  // V
  { code: "VA", label: "Vatican City", dialingCode: "+39" },
  {
    code: "VC",
    label: "Saint Vincent and the Grenadines",
    dialingCode: "+1784",
  },
  { code: "VE", label: "Venezuela", dialingCode: "+58" },
  { code: "VG", label: "British Virgin Islands", dialingCode: "+1284" },
  { code: "VI", label: "U.S. Virgin Islands", dialingCode: "+1340" },
  { code: "VN", label: "Vietnam", dialingCode: "+84" },
  { code: "VU", label: "Vanuatu", dialingCode: "+678" },

  // W
  { code: "WF", label: "Wallis and Futuna", dialingCode: "+681" },
  { code: "WS", label: "Samoa", dialingCode: "+685" },

  // Y
  { code: "YE", label: "Yemen", dialingCode: "+967" },
  { code: "YT", label: "Mayotte", dialingCode: "+262" },

  // Z
  { code: "ZA", label: "South Africa", dialingCode: "+27" },
  { code: "ZM", label: "Zambia", dialingCode: "+260" },
  { code: "ZW", label: "Zimbabwe", dialingCode: "+263" },
] as const;

// Utility functions
export const findCountryByCode = (code: string): CountryCode | undefined => {
  return countryCodes.find(
    (country) => country.code.toLowerCase() === code.toLowerCase()
  );
};

export const findCountryByDialingCode = (
  dialingCode: string
): CountryCode | undefined => {
  return countryCodes.find((country) => country.dialingCode === dialingCode);
};

export const findCountryByLabel = (label: string): CountryCode | undefined => {
  return countryCodes.find(
    (country) => country.label.toLowerCase() === label.toLowerCase()
  );
};

export const searchCountries = (searchTerm: string): CountryCode[] => {
  const term = searchTerm.toLowerCase();
  return countryCodes.filter(
    (country) =>
      country.label.toLowerCase().includes(term) ||
      country.code.toLowerCase().includes(term) ||
      country.dialingCode.includes(term)
  );
};

// Type for the country code values
export type CountryCodeValue = (typeof countryCodes)[number]["code"];
