/*
 * Copyright (C) 2015 Aidan "afollestad" Follestad
 * Copyright (C) 2018 Timothy "ZeevoX" Langer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * MIT License
 *
 * Copyright (c) 2017 Indrashish Ghosh
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package com.zeevox.octo.util;

import android.graphics.Color;
import android.support.annotation.ColorInt;

import java.util.ArrayList;
import java.util.List;

public class ColorUtils {

    /* From https://github.com/kabouzeid/app-theme-helper/blob/master/library/src/main/java/com/kabouzeid/appthemehelper/util/ColorUtil.java#L36 */
    public static boolean isColorLight(@ColorInt int color) {
        final double darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        return darkness < 0.4;
    }

    /* Adapted from https://github.com/ghosh/uiGradients/blob/40947e428c405146ceb75c37ea113e4b539acb6c/gradients.json */
    public static List<String[]> gradients = new ArrayList<String[]>() {
        {
            add(new String[]{"Blu", "#00416A", "#E4E5E6"});
            add(new String[]{"Ver", "#FFE000", "#799F0C"});
            add(new String[]{"Ver Black", "#F7F8F8", "#ACBB78"});
            add(new String[]{"Anwar", "#334d50", "#cbcaa5"});
            add(new String[]{"Reaqua", "#799F0C", "#ACBB78"});
            add(new String[]{"Mango", "#ffe259", "#ffa751"});
            add(new String[]{"Bupe", "#00416A", "#E4E5E6"});
            add(new String[]{"Rea", "#FFE000", "#799F0C"});
            add(new String[]{"Windy", "#acb6e5", "#86fde8"});
            add(new String[]{"Royal Blue", "#536976", "#292E49"});
            add(new String[]{"Copper", "#B79891", "#94716B"});
            add(new String[]{"Anamnisar", "#9796f0", "#fbc7d4"});
            add(new String[]{"Petrol", "#BBD2C5", "#536976"});
            add(new String[]{"Sky", "#076585", "#fff"});
            add(new String[]{"Sel", "#00467F", "#A5CC82"});
            add(new String[]{"Skyline", "#1488CC", "#2B32B2"});
            add(new String[]{"DIMIGO", "#ec008c", "#fc6767"});
            add(new String[]{"Purple Love", "#cc2b5e", "#753a88"});
            add(new String[]{"Sexy Blue", "#2193b0", "#6dd5ed"});
            add(new String[]{"Blooker20", "#e65c00", "#F9D423"});
            add(new String[]{"Sea Blue", "#2b5876", "#4e4376"});
            add(new String[]{"Nimvelo", "#314755", "#26a0da"});
            add(new String[]{"Noon to Dusk", "#ff6e7f", "#bfe9ff"});
            add(new String[]{"YouTube", "#e52d27", "#b31217"});
            add(new String[]{"Cool Brown", "#603813", "#b29f94"});
            add(new String[]{"Harmonic Energy", "#16A085", "#F4D03F"});
            add(new String[]{"Playing with Reds", "#D31027", "#EA384D"});
            add(new String[]{"Sunny Days", "#EDE574", "#E1F5C4"});
            add(new String[]{"Green Beach", "#02AAB0", "#00CDAC"});
            add(new String[]{"Intuitive Purple", "#DA22FF", "#9733EE"});
            add(new String[]{"Emerald Water", "#348F50", "#56B4D3"});
            add(new String[]{"Lemon Twist", "#3CA55C", "#B5AC49"});
            add(new String[]{"Horizon", "#003973", "#E5E5BE"});
            add(new String[]{"Rose Water", "#E55D87", "#5FC3E4"});
            add(new String[]{"Frozen", "#403B4A", "#E7E9BB"});
            add(new String[]{"Mango Pulp", "#F09819", "#EDDE5D"});
            add(new String[]{"Bloody Mary", "#FF512F", "#DD2476"});
            add(new String[]{"Aubergine", "#AA076B", "#61045F"});
            add(new String[]{"Aqua Marine", "#1A2980", "#26D0CE"});
            add(new String[]{"Sunrise", "#FF512F", "#F09819"});
            add(new String[]{"Purple Paradise", "#1D2B64", "#F8CDDA"});
            add(new String[]{"Sea Weed", "#4CB8C4", "#3CD3AD"});
            add(new String[]{"Pinky", "#DD5E89", "#F7BB97"});
            add(new String[]{"Cherry", "#EB3349", "#F45C43"});
            add(new String[]{"Mojito", "#1D976C", "#93F9B9"});
            add(new String[]{"Juicy Orange", "#FF8008", "#FFC837"});
            add(new String[]{"Mirage", "#16222A", "#3A6073"});
            add(new String[]{"Steel Gray", "#1F1C2C", "#928DAB"});
            add(new String[]{"Kashmir", "#614385", "#516395"});
            add(new String[]{"Electric Violet", "#4776E6", "#8E54E9"});
            add(new String[]{"Venice Blue", "#085078", "#85D8CE"});
            add(new String[]{"Bora Bora", "#2BC0E4", "#EAECC6"});
            add(new String[]{"Moss", "#134E5E", "#71B280"});
            add(new String[]{"Shroom Haze", "#5C258D", "#4389A2"});
            add(new String[]{"Mystic", "#757F9A", "#D7DDE8"});
            add(new String[]{"Midnight City", "#232526", "#414345"});
            add(new String[]{"Sea Blizz", "#1CD8D2", "#93EDC7"});
            add(new String[]{"Opa", "#3D7EAA", "#FFE47A"});
            add(new String[]{"Titanium", "#283048", "#859398"});
            add(new String[]{"Mantle", "#24C6DC", "#514A9D"});
            add(new String[]{"Dracula", "#DC2424", "#4A569D"});
            add(new String[]{"Peach", "#ED4264", "#FFEDBC"});
            add(new String[]{"Moonrise", "#DAE2F8", "#D6A4A4"});
            add(new String[]{"Clouds", "#ECE9E6", "#FFFFFF"});
            add(new String[]{"Stellar", "#7474BF", "#348AC7"});
            add(new String[]{"Bourbon", "#EC6F66", "#F3A183"});
            add(new String[]{"Calm Darya", "#5f2c82", "#49a09d"});
            add(new String[]{"Influenza", "#C04848", "#480048"});
            add(new String[]{"Shrimpy", "#e43a15", "#e65245"});
            add(new String[]{"Army", "#414d0b", "#727a17"});
            add(new String[]{"Miaka", "#FC354C", "#0ABFBC"});
            add(new String[]{"Pinot Noir", "#4b6cb7", "#182848"});
            add(new String[]{"Day Tripper", "#f857a6", "#ff5858"});
            add(new String[]{"Namn", "#a73737", "#7a2828"});
            add(new String[]{"Blurry Beach", "#d53369", "#cbad6d"});
            add(new String[]{"Vasily", "#e9d362", "#333333"});
            add(new String[]{"A Lost Memory", "#DE6262", "#FFB88C"});
            add(new String[]{"Petrichor", "#666600", "#999966"});
            add(new String[]{"Jonquil", "#FFEEEE", "#DDEFBB"});
            add(new String[]{"Sirius Tamed", "#EFEFBB", "#D4D3DD"});
            add(new String[]{"Kyoto", "#c21500", "#ffc500"});
            add(new String[]{"Misty Meadow", "#215f00", "#e4e4d9"});
            add(new String[]{"Aqualicious", "#50C9C3", "#96DEDA"});
            add(new String[]{"Moor", "#616161", "#9bc5c3"});
            add(new String[]{"Almost", "#ddd6f3", "#faaca8"});
            add(new String[]{"Forever Lost", "#5D4157", "#A8CABA"});
            add(new String[]{"Winter", "#E6DADA", "#274046"});
            add(new String[]{"Nelson", "#f2709c", "#ff9472"});
            add(new String[]{"Autumn", "#DAD299", "#B0DAB9"});
            add(new String[]{"Candy", "#D3959B", "#BFE6BA"});
            add(new String[]{"Reef", "#00d2ff", "#3a7bd5"});
            add(new String[]{"The Strain", "#870000", "#190A05"});
            add(new String[]{"Dirty Fog", "#B993D6", "#8CA6DB"});
            add(new String[]{"Earthly", "#649173", "#DBD5A4"});
            add(new String[]{"Virgin", "#C9FFBF", "#FFAFBD"});
            add(new String[]{"Ash", "#606c88", "#3f4c6b"});
            add(new String[]{"Cherryblossoms", "#FBD3E9", "#BB377D"});
            add(new String[]{"Parklife", "#ADD100", "#7B920A"});
            add(new String[]{"Dance To Forget", "#FF4E50", "#F9D423"});
            add(new String[]{"Starfall", "#F0C27B", "#4B1248"});
            add(new String[]{"Red Mist", "#000000", "#e74c3c"});
            add(new String[]{"Teal Love", "#AAFFA9", "#11FFBD"});
            add(new String[]{"Neon Life", "#B3FFAB", "#12FFF7"});
            add(new String[]{"Man of Steel", "#780206", "#061161"});
            add(new String[]{"Amethyst", "#9D50BB", "#6E48AA"});
            add(new String[]{"Cheer Up Emo Kid", "#556270", "#FF6B6B"});
            add(new String[]{"Shore", "#70e1f5", "#ffd194"});
            add(new String[]{"Facebook Messenger", "#00c6ff", "#0072ff"});
            add(new String[]{"SoundCloud", "#fe8c00", "#f83600"});
            add(new String[]{"Behongo", "#52c234", "#061700"});
            add(new String[]{"ServQuick", "#485563", "#29323c"});
            add(new String[]{"Friday", "#83a4d4", "#b6fbff"});
            add(new String[]{"Martini", "#FDFC47", "#24FE41"});
            add(new String[]{"Metallic Toad", "#abbaab", "#ffffff"});
            add(new String[]{"Between The Clouds", "#73C8A9", "#373B44"});
            add(new String[]{"Crazy Orange I", "#D38312", "#A83279"});
            add(new String[]{"Hersheys", "#1e130c", "#9a8478"});
            add(new String[]{"Talking To Mice Elf", "#948E99", "#2E1437"});
            add(new String[]{"Purple Bliss", "#360033", "#0b8793"});
            add(new String[]{"Predawn", "#FFA17F", "#00223E"});
            add(new String[]{"Endless River", "#43cea2", "#185a9d"});
            add(new String[]{"Pastel Orange at the Sun", "#ffb347", "#ffcc33"});
            add(new String[]{"Twitch", "#6441A5", "#2a0845"});
            add(new String[]{"Flickr", "#ff0084", "#33001b"});
            add(new String[]{"Vine", "#00bf8f", "#001510"});
            add(new String[]{"Turquoise flow", "#136a8a", "#267871"});
            add(new String[]{"Portrait", "#8e9eab", "#eef2f3"});
            add(new String[]{"Virgin America", "#7b4397", "#dc2430"});
            add(new String[]{"Koko Caramel", "#D1913C", "#FFD194"});
            add(new String[]{"Fresh Turboscent", "#F1F2B5", "#135058"});
            add(new String[]{"Green to dark", "#6A9113", "#141517"});
            add(new String[]{"Ukraine", "#004FF9", "#FFF94C"});
            add(new String[]{"Curiosity blue", "#525252", "#3d72b4"});
            add(new String[]{"Dark Knight", "#BA8B02", "#181818"});
            add(new String[]{"Piglet", "#ee9ca7", "#ffdde1"});
            add(new String[]{"Lizard", "#304352", "#d7d2cc"});
            add(new String[]{"Sage Persuasion", "#CCCCB2", "#757519"});
            add(new String[]{"Between Night and Day", "#2c3e50", "#3498db"});
            add(new String[]{"Timber", "#fc00ff", "#00dbde"});
            add(new String[]{"Passion", "#e53935", "#e35d5b"});
            add(new String[]{"Clear Sky", "#005C97", "#363795"});
            add(new String[]{"Master Card", "#f46b45", "#eea849"});
            add(new String[]{"Back To Earth", "#00C9FF", "#92FE9D"});
            add(new String[]{"Deep Purple", "#673AB7", "#512DA8"});
            add(new String[]{"Little Leaf", "#76b852", "#8DC26F"});
            add(new String[]{"Netflix", "#8E0E00", "#1F1C18"});
            add(new String[]{"Light Orange", "#FFB75E", "#ED8F03"});
            add(new String[]{"Green and Blue", "#c2e59c", "#64b3f4"});
            add(new String[]{"Poncho", "#403A3E", "#BE5869"});
            add(new String[]{"Back to the Future", "#C02425", "#F0CB35"});
            add(new String[]{"Blush", "#B24592", "#F15F79"});
            add(new String[]{"Inbox", "#457fca", "#5691c8"});
            add(new String[]{"Purplin", "#6a3093", "#a044ff"});
            add(new String[]{"Pale Wood", "#eacda3", "#d6ae7b"});
            add(new String[]{"Haikus", "#fd746c", "#ff9068"});
            add(new String[]{"Pizelex", "#114357", "#F29492"});
            add(new String[]{"Joomla", "#1e3c72", "#2a5298"});
            add(new String[]{"Christmas", "#2F7336", "#AA3A38"});
            add(new String[]{"Minnesota Vikings", "#5614B0", "#DBD65C"});
            add(new String[]{"Miami Dolphins", "#4DA0B0", "#D39D38"});
            add(new String[]{"Forest", "#5A3F37", "#2C7744"});
            add(new String[]{"Nighthawk", "#2980b9", "#2c3e50"});
            add(new String[]{"Superman", "#0099F7", "#F11712"});
            add(new String[]{"Suzy", "#834d9b", "#d04ed6"});
            add(new String[]{"Dark Skies", "#4B79A1", "#283E51"});
            add(new String[]{"Deep Space", "#000000", "#434343"});
            add(new String[]{"Decent", "#4CA1AF", "#C4E0E5"});
            add(new String[]{"Colors Of Sky", "#E0EAFC", "#CFDEF3"});
            add(new String[]{"Purple White", "#BA5370", "#F4E2D8"});
            add(new String[]{"Ali", "#ff4b1f", "#1fddff"});
            add(new String[]{"Alihossein", "#f7ff00", "#db36a4"});
            add(new String[]{"Shahabi", "#a80077", "#66ff00"});
            add(new String[]{"Red Ocean", "#1D4350", "#A43931"});
            add(new String[]{"Tranquil", "#EECDA3", "#EF629F"});
            add(new String[]{"Transfile", "#16BFFD", "#CB3066"});
            add(new String[]{"Sylvia", "#ff4b1f", "#ff9068"});
            add(new String[]{"Sweet Morning", "#FF5F6D", "#FFC371"});
            add(new String[]{"Politics", "#2196f3", "#f44336"});
            add(new String[]{"Bright Vault", "#00d2ff", "#928DAB"});
            add(new String[]{"Solid Vault", "#3a7bd5", "#3a6073"});
            add(new String[]{"Sunset", "#0B486B", "#F56217"});
            add(new String[]{"Grapefruit Sunset", "#e96443", "#904e95"});
            add(new String[]{"Deep Sea Space", "#2C3E50", "#4CA1AF"});
            add(new String[]{"Dusk", "#2C3E50", "#FD746C"});
            add(new String[]{"Minimal Red", "#F00000", "#DC281E"});
            add(new String[]{"Royal", "#141E30", "#243B55"});
            add(new String[]{"Mauve", "#42275a", "#734b6d"});
            add(new String[]{"Frost", "#000428", "#004e92"});
            add(new String[]{"Lush", "#56ab2f", "#a8e063"});
            add(new String[]{"Firewatch", "#cb2d3e", "#ef473a"});
            add(new String[]{"Sherbert", "#f79d00", "#64f38c"});
            add(new String[]{"Blood Red", "#f85032", "#e73827"});
            add(new String[]{"Sun on the Horizon", "#fceabb", "#f8b500"});
            add(new String[]{"IIIT Delhi", "#808080", "#3fada8"});
            add(new String[]{"Jupiter", "#ffd89b", "#19547b"});
            add(new String[]{"50 Shades of Grey", "#bdc3c7", "#2c3e50"});
            add(new String[]{"Dania", "#BE93C5", "#7BC6CC"});
            add(new String[]{"Limeade", "#A1FFCE", "#FAFFD1"});
            add(new String[]{"Disco", "#4ECDC4", "#556270"});
            add(new String[]{"Love Couple", "#3a6186", "#89253e"});
            add(new String[]{"Azure Pop", "#ef32d9", "#89fffd"});
            add(new String[]{"Nepal", "#de6161", "#2657eb"});
            add(new String[]{"Cosmic Fusion", "#ff00cc", "#333399"});
            add(new String[]{"Snapchat", "#fffc00", "#ffffff"});
            add(new String[]{"Ed's Sunset Gradient", "#ff7e5f", "#feb47b"});
            add(new String[]{"Brady Brady Fun Fun", "#00c3ff", "#ffff1c"});
            add(new String[]{"Black Rosé", "#f4c4f3", "#fc67fa"});
            add(new String[]{"80's Purple", "#41295a", "#2F0743"});
            add(new String[]{"Ibiza Sunset", "#ee0979", "#ff6a00"});
            add(new String[]{"Dawn", "#F3904F", "#3B4371"});
            add(new String[]{"Mild", "#67B26F", "#4ca2cd"});
            add(new String[]{"Vice City", "#3494E6", "#EC6EAD"});
            add(new String[]{"Jaipur", "#DBE6F6", "#C5796D"});
            add(new String[]{"Cocoaa Ice", "#c0c0aa", "#1cefff"});
            add(new String[]{"EasyMed", "#DCE35B", "#45B649"});
            add(new String[]{"Rose Colored Lenses", "#E8CBC0", "#636FA4"});
            add(new String[]{"What lies Beyond", "#F0F2F0", "#000C40"});
            add(new String[]{"Roseanna", "#FFAFBD", "#ffc3a0"});
            add(new String[]{"Honey Dew", "#43C6AC", "#F8FFAE"});
            add(new String[]{"Under the Lake", "#093028", "#237A57"});
            add(new String[]{"The Blue Lagoon", "#43C6AC", "#191654"});
            add(new String[]{"Can You Feel The Love Tonight", "#4568DC", "#B06AB3"});
            add(new String[]{"Very Blue", "#0575E6", "#021B79"});
            add(new String[]{"Love and Liberty", "#200122", "#6f0000"});
            add(new String[]{"Orca", "#44A08D", "#093637"});
            add(new String[]{"Venice", "#6190E8", "#A7BFE8"});
            add(new String[]{"Pacific Dream", "#34e89e", "#0f3443"});
            add(new String[]{"Learning and Leading", "#F7971E", "#FFD200"});
            add(new String[]{"Celestial", "#C33764", "#1D2671"});
            add(new String[]{"Purplepine", "#20002c", "#cbb4d4"});
            add(new String[]{"Sha la la", "#D66D75", "#E29587"});
            add(new String[]{"Mini", "#30E8BF", "#FF8235"});
            add(new String[]{"Maldives", "#B2FEFA", "#0ED2F7"});
            add(new String[]{"Cinnamint", "#4AC29A", "#BDFFF3"});
            add(new String[]{"Html", "#E44D26", "#F16529"});
            add(new String[]{"Coal", "#EB5757", "#000000"});
            add(new String[]{"Sunkist", "#F2994A", "#F2C94C"});
            add(new String[]{"Blue Skies", "#56CCF2", "#2F80ED"});
            add(new String[]{"Chitty Chitty Bang Bang", "#007991", "#78ffd6"});
            add(new String[]{"Visions of Grandeur", "#000046", "#1CB5E0"});
            add(new String[]{"Crystal Clear", "#159957", "#155799"});
            add(new String[]{"Mello", "#c0392b", "#8e44ad"});
            add(new String[]{"Compare Now", "#EF3B36", "#FFFFFF"});
            add(new String[]{"Meridian", "#283c86", "#45a247"});
            add(new String[]{"Alive", "#CB356B", "#BD3F32"});
            add(new String[]{"Scooter", "#36D1DC", "#5B86E5"});
            add(new String[]{"Terminal", "#000000", "#0f9b0f"});
            add(new String[]{"Telegram", "#1c92d2", "#f2fcfe"});
            add(new String[]{"Crimson Tide", "#642B73", "#C6426E"});
            add(new String[]{"Socialive", "#06beb6", "#48b1bf"});
            add(new String[]{"Broken Hearts", "#d9a7c7", "#fffcdc"});
            add(new String[]{"Kimoby Is The New Blue", "#396afc", "#2948ff"});
            add(new String[]{"Dull", "#C9D6FF", "#E2E2E2"});
            add(new String[]{"Purpink", "#7F00FF", "#E100FF"});
            add(new String[]{"Orange Coral", "#ff9966", "#ff5e62"});
            add(new String[]{"Summer", "#22c1c3", "#fdbb2d"});
            add(new String[]{"Velvet Sun", "#e1eec3", "#f05053"});
            add(new String[]{"Lithium", "#6D6027", "#D3CBB8"});
            add(new String[]{"Digital Water", "#74ebd5","#ACB6E5"});
            add(new String[]{"Orange Fun", "#fc4a1a", "#f7b733"});
            add(new String[]{"Rainbow Blue", "#00F260", "#0575E6"});
            add(new String[]{"Pink Flavour", "#800080", "#ffc0cb"});
            add(new String[]{"Sulphur", "#CAC531", "#F3F9A7"});
            add(new String[]{"Selenium", "#3C3B3F", "#605C3C"});
            add(new String[]{"Delicate", "#D3CCE3", "#E9E4F0"});
            add(new String[]{"Ohhappiness", "#00b09b", "#96c93d"});
            add(new String[]{"Relaxing red", "#fffbd5", "#b20a2c"});
            add(new String[]{"Taran Tado", "#23074d", "#cc5333"});
            add(new String[]{"Bighead", "#c94b4b", "#4b134f"});
            add(new String[]{"Sublime Vivid", "#FC466B", "#3F5EFB"});
            add(new String[]{"Sublime Light", "#FC5C7D", "#6A82FB"});
            add(new String[]{"Pun Yeta", "#108dc7", "#ef8e38"});
            add(new String[]{"Quepal", "#11998e", "#38ef7d"});
            add(new String[]{"Sand to Blue", "#3E5151", "#DECBA4"});
            add(new String[]{"Shifter", "#bc4e9c", "#f80759"});
            add(new String[]{"Moon Purple", "#4e54c8", "#8f94fb"});
            add(new String[]{"Pure Lust", "#333333", "#dd1818"});
            add(new String[]{"Slight Ocean View", "#a8c0ff", "#3f2b96"});
            add(new String[]{"eXpresso", "#ad5389", "#3c1053"});
            add(new String[]{"Shifty", "#636363", "#a2ab58"});
            add(new String[]{"Vanusa", "#DA4453", "#89216B"});
            add(new String[]{"Evening Night", "#005AA7", "#FFFDE4"});
            add(new String[]{"Margo", "#FFEFBA", "#FFFFFF"});
            add(new String[]{"Blue Raspberry", "#00B4DB", "#0083B0"});
            add(new String[]{"Citrus Peel", "#FDC830", "#F37335"});
            add(new String[]{"Sin City Red", "#ED213A", "#93291E"});
            add(new String[]{"Summer Dog", "#a8ff78", "#78ffd6"});
            add(new String[]{"Burning Orange", "#FF416C", "#FF4B2B"});
            add(new String[]{"Ultra Voilet", "#654ea3", "#eaafc8"});
            add(new String[]{"By Design", "#009FFF", "#ec2F4B"});
            add(new String[]{"Kyoo Tah", "#544a7d", "#ffd452"});
            add(new String[]{"Kye Meh", "#8360c3", "#2ebf91"});
            add(new String[]{"Kyoo Pal", "#dd3e54", "#6be585"});
            add(new String[]{"Metapolis", "#659999", "#f4791f"});
            add(new String[]{"Flare", "#f12711", "#f5af19"});
            add(new String[]{"Witching Hour", "#c31432", "#240b36"});
            add(new String[]{"Neuromancer", "#f953c6", "#b91d73"});
            add(new String[]{"Harvey", "#1f4037", "#99f2c8"});
            add(new String[]{"Amin", "#8E2DE2", "#4A00E0"});
            add(new String[]{"Yoda", "#FF0099", "#493240"});
            add(new String[]{"Dark Ocean", "#373B44", "#4286f4"});
            add(new String[]{"Evening Sunshine", "#b92b27", "#1565C0"});
        }
    };

    public static String[] getGradientDetails(int position) {
        return gradients.get(position);
    }

    public static String getGradientName(String[] gradientInfo) {
        return gradientInfo[0];
    }

    public static int getGradientStartColor(String[] gradientInfo) {
        return Color.parseColor(gradientInfo[1]);
    }

    public static int getGradientEndColor(String[] gradientInfo) {
        return Color.parseColor(gradientInfo[2]);
    }
}
