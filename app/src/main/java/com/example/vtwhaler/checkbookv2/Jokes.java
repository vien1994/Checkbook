package com.example.vtwhaler.checkbookv2;

/**
 * Created by VTWhaler on 8/17/2017.
 */


public class Jokes {
    private static String result;

    public String getJoke() {
        int randNum = (int) Math.floor(Math.random() * 95); //num of options including 0. ex: *95 means up to case 94.
        switch (randNum) {
            case 0:
                result= "a";
                break;
            case 1:
                result= "How do you find Will Smith in the snow?\n\nYou look for Fresh Prints.";
                break;
            case 2:
                result= "What's the funniest candy bar?\n\nSnickers." ;
                break;
            case 3:
                result= "I'm shook";
                break;
            case 4:
                result= "Why aren't Koalas actual bears?\n\nBecause they don't meet the Koalafications.";
                break;
            case 5:
                result= "I would do a steak joke, but they're never well done.";
                break;
            case 6:
                result= "Did you hear about the lumberjack who got fired for cutting too many trees?\nHe saw too much.";
                break;
            case 7:
                result= "What's Forrest Gump's wifi password?\n\n1Forrest1.";
                break;
            case 8:
                result= "What do you call a fish with no eyes?\n\nFSH";
                break;
            case 9:
                result= "Want to know the last thing my grandpa said before he kicked the bucket?\n\"Want to see how far I can kick this bucket?\"";
                break;
            case 10:
                result= "What do you call a group of killer whales playing instruments?\n\nAn Orca-Stra";
                break;
            case 11:
                result= "What did Michael Jackson call his Denim store?\n\nBillie Jeans";
                break;
            case 12:
                result= "How do billboards talk?\n\nSign Language.";
                break;
            case 13:
                result= "What rapper is in a toolbox?\n\nPlies.";
                break;
            case 14:
                result= "Why couldn't the toilet paper cross the road?\n\nIt got stuck in the crack.";
                break;
            case 15:
                result= "What did the fat person say to the pig?\n\nDA-HAAAM!";
                break;
            case 16:
                result= "When does a sandwich cook?\n\nWhen it's bakin lettuce and tomato!";
                break;
            case 17:
                result= "Did you hear about the female rapper who only battled when she was on her menstrual cycle?\nThey said she had a mean flow.";
                break;
            case 18:
                result= "How many kids with ADHD does it take to change a light bulb?\n\nLets go play with our bikes!";
                break;
            case 19:
                result= "Do you know why I stopped wanting to be a banker? \n\nI lost interest";
                break;
            case 20:
                result= "I started a band called 999 Gigabytes.\n\nBut we didn't get any gigs yet.";
                break;
            case 21:
                result= "A blind man walks into a bar. And a chair. And a table. And people.";
                break;
            case 22:
                result= "Why can't you hear a pteradactyl go to the bathroom?\n\nBecause the pee is silent.";
                break;
            case 23:
                result= "Did you hear about the hungry clock?\n\nIt went back four seconds.";
                break;
            case 24:
                result= "You know what happens when you get a bladder infections?\n\nUrine Trouble.";
                break;
            case 25:
                result= "What has four wheels and flies?\n\nA garbage truck.";
                break;
            case 26:
                result= "What do you call a fat psychic?\n\nA 4-Chin-Teller";
                break;
            case 27:
                result= "How did the hipster burn his tongue?\n\nHe drank his coffee before it was cool.";
                break;
            case 28:
                result= "Why were the two melons sad?\n\nBecause they canteloupe.";
                break;
            case 29:
                result= "Why did the gangster go see the eye doctor?\n\nBecause he had Glock-oma";
                break;
            case 30:
                result= "Why do white girls come in odd numbers?\n\nBecause they cant even.";
                break;
            case 31:
                result= "What do you do with a dead chemist?\n\nYou Barium.";
                break;
            case 32:
                result= "What did the Buddhist say when he went up to the hot dog vendor?\n\nMake me one with everything";
                break;
            case 33:
                result= "What do you call a cow with no legs?\n\nGround Beef.";
                break;
            case 34:
                result= "What do you call a cow with only 3 legs?\n\nTri-tip.";
                break;
            case 35:
                result= "What did the farmer say to the cow that can't give milk?\n\nWhat a complete and udder failure!";
                break;
            case 36:
                result= "What do you call a police officer in bed?\n\nAn undercover cop";
                break;
            case 37:
                result= "What do you call cheese that's not yours?\n\nNacho Cheese!";
                break;
            case 38:
                result= "What did the hat say to the other hat?\n\nYou stay here, I'm going to go on A Head.";
                break;
            case 39:
                result= "Why did the energizer bunny get arrested?\n\nHe was charged with battery.";
                break;
            case 40:
                result= "How does Moses make his coffee?\n\nHebrews it.";
                break;
            case 41:
                result= "What video game do you play on the toilet?\n\nCall Of Doodie.";
                break;
            case 42:
                result= "What happens when a frog parks illegally?\n\nIt gets Toad.";
                break;
            case 43:
                result= "What did the taxi driver say to the wolf?\n\nWhere wolf?";
                break;
            case 44:
                result= "What concert costs 45 cents?\n\n50 Cent ft. Nickelback.";
                break;
            case 45:
                result= "What's the difference between snowmen and snowwoman?\n\nSnowballs.";
                break;
            case 46:
                result= "What did the grape say when he got stepped on?\n\nNothing. He just let out a little wine.";
                break;
            case 47:
                result= "What did the little boy say when he accidentally swallowed food coloring?\n\nI think I dyed a little inside.";
                break;
            case 48:
                result= "I wasn't originally going to get a brain transplant, but then I changed my mind.";
                break;
            case 49:
                result= "I'd tell you a chemistry joke but I know I wouldn't get a reaction.";
                break;
            case 50:
                result= "Want to hear a joke about a broken pencil?\nNevermind. It would've been pointless.";
                break;
            case 51:
                result= "Why don't some couples go to the gym? Because some relationships don't work out.";
                break;
            case 52:
                result= "I wondered why the baseball was getting bigger. Then it hit me.";
                break;
            case 53:
                result= "A friend of mine tried to annoy me with bird puns, but I soon realized that toucan play at that game.";
                break;
            case 54:
                result= "Did you hear about the guy who got hit in the head with a can of soda? He was lucky it was a soft drink.";
                break;
            case 55:
                result= "Have you ever tried to eat a clock? It's very time consuming.";
                break;
            case 56:
                result= "The old carpenter really nailed it, but the new guy screwed everything up.";
                break;
            case 57:
                result= "What's green and has wheels?\n\nGrass. I lied about the wheels.";
                break;
            case 58:
                result= "Do you know what I'm doing when I look at your face?\n\nEyebrows.";
                break;
            case 59:
                result= "Yo mama is so dumb she put lipstick on her forehead just to make up her mind.";
                break;
            case 60:
                result= "Yo mama is so fat that when she went to In-N-Out she couldn't get IN OR OUT.";
                break;
            case 61:
                result= "Women only call me ugly until they find out how much money I make.\n\nThen they call me ugly and poor.";
                break;
            case 62:
                result= "Someone stole my Microsoft Office and they're gonna pay.\n\nYou have my Word.";
                break;
            case 63:
                result= "I went to a really emotional wedding the other day.\nEven the cake was in tiers.";
                break;
            case 64:
                result= "We have a genetic predisposition for diarrhea.\n\nRuns in our jeans.";
                break;
            case 65:
                result= "A physicist sees a young man about to jump off the Empire State Building.\n\nHe yells \"Don't do it! You have so much potential!\"";
                break;
            case 66:
                result= "Want to hear a word I just made up?\n\nPlagiarism.";
                break;
            case 67:
                result= "Why do cows wear bells?\n\nBecause their horns don't work.";
                break;
            case 68:
                result= "To the handicapped guy who stole my bag -\nYou can hide but you can't run.";
                break;
            case 69:
                result= "I took the shell off my racing snail, thinking it would make him run faster.\n\nIf anything, it made him more sluggish.";
                break;
            case 70:
                result= "And the Lord said unto John, \"Come forth and you will receive eteranl life\"\n\nBut John came fifth, and won a toaster.";
                break;
            case 71:
                result= "Someone stole my mood ring.\n\nI don't know how I feel about that.";
                break;
            case 72:
                result= "I told my wife she was drawing her eyebrows too high.\n\nShe looked surprised.";
                break;
            case 73:
                result= "How do you make a tissue dance?\n\nYou put a little boogie in it.";
                break;
            case 74:
                result= "Why did the policeman smell bad?\n\nHe was on doodie.";
                break;
            case 75:
                result= "I never make mistakes... I thought I did once; but I was wrong.";
                break;
            case 76:
                result= "What's the last thing that goes through a bug's mind as he hits the windshield?\n\nHis butt.";
                break;
            case 77:
                result= "The past, present and future walk into a bar. It was tense.";
                break;
            case 78:
                result= "What's brown and sticky?\n\nA stick.";
                break;
            case 79:
                result= "What do you get when you throw a piano down a mine shaft?\n\nA flat miner.";
                break;
            case 80:
                result= "Why was Tigger looking in the toilet?\n\nHe was looking for Pooh!";
                break;
            case 81:
                result= "What do you get when you put a candle in a suit of armor?\n\nA knight light.";
                break;
            case 82:
                result= "Two drums and a cymbal fall off a cliff.\n\nBa-dum Tish!";
                break;
            case 83:
                result= "There were two peanuts walking down a dark alley, one was assaulted.";
                break;
            case 84:
                result= "What do you call a sleepwalking nun?\n\nA roamin' Catholic.";
                break;
            case 85:
                result= "How do you make holy water?\n\nYou boil the hell out of it.";
                break;
            case 86:
                result= "Why did the storm trooper buy an iphone?\n\nHe couldn’t find the Droid he was looking for.";
                break;
            case 87:
                result= "How does Jesus make tea?\n\n Hebrews it.";
                break;
            case 88:
                result= "If you’re American in the living room, what are you in the bathroom?\n\nEuropean!";
                break;
            case 89:
                result= "A dyslexic man walks into a bra.";
                break;
            case 90:
                result= "How many tickles does it take to make an Octopus laugh?\n\nTen-tickles.";
                break;
            case 91:
                result= "Why can't you surf microwaves?\n\nBecause they're too small.";
                break;
            case 92:
                result= "What did Batman say to Robin before getting in the Batmobile?\n\nGet in the car.";
                break;
            case 93:
                result= "Is this the Krusty Krab?\n\nNo, this is Patrick.";
                break;
            case 94:
                result= "If your boyfriend can't appreciate a good fruit pun, let that mango.";
                break;
            case 95:
                result= "";
                break;
            case 96:
                result= "";
                break;
            case 97:
                result= "";
                break;
            case 98:
                result= "";
                break;
            case 99:
                result= "";
                break;
            case 100:
                result= "";
                break;
        }
        return result;
    }
}
