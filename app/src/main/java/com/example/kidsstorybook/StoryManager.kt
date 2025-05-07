package com.example.kidsstorybook

import com.example.kidsstorybook.model.Story

class StoryManager {
    // List of stories with translations
    private val stories = listOf(
        Story(
            id = 1,
            title = "The Little Red Hen",
            text = "Once upon a time, there was a little red hen who found some wheat seeds. She asked her friends, 'Who will help me plant these seeds?'",
            content = "Once upon a time, there was a little red hen who found some wheat seeds. She asked her friends, 'Who will help me plant these seeds?' The duck, the cat, and the dog all said they were too busy. So the little red hen planted the seeds herself. When the wheat grew, she asked again, 'Who will help me harvest the wheat?' Again, her friends were too busy. The little red hen harvested the wheat herself. Finally, she asked, 'Who will help me bake bread with this wheat?' Her friends still refused. So the little red hen baked the bread herself. When the bread was ready, her friends wanted to eat it, but the little red hen said, 'No, I did all the work myself, so I will eat the bread myself.'",
            arabicContent = "كان هناك دجاجة حمراء صغيرة وجدت بعض بذور القمح. سألت أصدقاءها، 'من سيساعدني في زراعة هذه البذور؟' قال البط والقط والكلب إنهم مشغولون جداً. فزرعت الدجاجة الحمراء الصغيرة البذور بنفسها. عندما نما القمح، سألت مرة أخرى، 'من سيساعدني في حصاد القمح؟' مرة أخرى، كان أصدقاؤها مشغولين. حصدت الدجاجة الحمراء الصغيرة القمح بنفسها. أخيراً، سألت، 'من سيساعدني في خبز الخبز بهذا القمح؟' رفض أصدقاؤها مرة أخرى. فخبزت الدجاجة الحمراء الصغيرة الخبز بنفسها. عندما كان الخبز جاهزاً، أراد أصدقاؤها تناوله، لكن الدجاجة الحمراء الصغيرة قالت، 'لا، أنا قمت بكل العمل بنفسي، لذا سأتناول الخبز بنفسي.'",
            turkishContent = "Bir zamanlar biraz buğday tohumu bulan küçük kırmızı bir tavuk vardı. Arkadaşlarına sordu, 'Bu tohumları ekmeme kim yardım edecek?' Ördek, kedi ve köpek hepsi çok meşgul olduklarını söylediler. Böylece küçük kırmızı tavuk tohumları kendisi ekti. Buğday büyüdüğünde, tekrar sordu, 'Buğdayı biçmeme kim yardım edecek?' Yine arkadaşları çok meşguldü. Küçük kırmızı tavuk buğdayı kendisi biçti. Son olarak sordu, 'Bu buğdayla ekmek yapmama kim yardım edecek?' Arkadaşları yine reddetti. Böylece küçük kırmızı tavuk ekmeği kendisi pişirdi. Ekmek hazır olduğunda, arkadaşları yemek istedi, ama küçük kırmızı tavuk dedi ki, 'Hayır, tüm işi ben kendim yaptım, bu yüzden ekmeği ben kendim yiyeceğim.'",
            imageResId = 0, // Placeholder, replace with actual resource ID if available
            availableLanguages = listOf("English", "Arabic", "Turkish")
        ),
        Story(
            title = "The Three Little Pigs",
            content = "Once upon a time, there were three little pigs who went out into the world to build their own houses. The first pig built his house with straw, the second with sticks, and the third with bricks. One day, a big bad wolf came and blew down the houses of straw and sticks. The pigs ran to their brother's brick house, which the wolf could not blow down. The wolf tried to come down the chimney, but the third pig had a pot of boiling water ready. The wolf fell into the pot and ran away, never to bother the pigs again.",
            arabicContent = "كان هناك ثلاثة خنازير صغيرة خرجت إلى العالم لبناء منازلهم الخاصة. بنى الخنزير الأول منزله من القش، والثاني من العصي، والثالث من الطوب. ذات يوم، جاء ذئب كبير سيء ونفخ منازل القش والعصي. هرب الخنازير إلى منزل أخيهم المصنوع من الطوب، الذي لم يستطع الذئب نفخه. حاول الذئب النزول من المدخنة، لكن الخنزير الثالث كان لديه وعاء من الماء المغلي جاهز. سقط الذئب في الوعاء وهرب، ولم يزعج الخنازير مرة أخرى.",
            turkishContent = "Bir zamanlar kendi evlerini inşa etmek için dünyaya çıkan üç küçük domuz vardı. İlk domuz evini samandan, ikincisi çubuklardan ve üçüncüsü tuğlalardan yaptı. Bir gün, büyük kötü kurt geldi ve saman ve çubuk evleri üfledi. Domuzlar kurdun üfleyemediği tuğla evlerine kaçtılar. Kurt bacadan inmeye çalıştı, ama üçüncü domuzun kaynar su dolu bir kazanı hazırdı. Kurt kazana düştü ve bir daha domuzları rahatsız etmemek üzere kaçtı."
        ),
        Story(
            title = "Goldilocks and the Three Bears",
            content = "Once upon a time, a little girl named Goldilocks went for a walk in the forest and found a house belonging to three bears. She went inside and tried their porridge. The first bowl was too hot, the second too cold, but the third was just right. Then she tried their chairs. The first was too hard, the second too soft, but the third was just right. Finally, she tried their beds. The first was too hard, the second too soft, but the third was just right, and she fell asleep. When the bears came home, they found Goldilocks in their house. She woke up and ran away, never to return.",
            arabicContent = "ذات مرة، ذهبت فتاة صغيرة تدعى جولديلوكس في نزهة في الغابة ووجدت منزلاً يخص ثلاثة دببة. دخلت وجربت عصيدتهم. الوعاء الأول كان ساخناً جداً، والثاني بارداً جداً، لكن الثالث كان مناسباً تماماً. ثم جربت كراسيهم. الأول كان قاسياً جداً، والثاني ناعماً جداً، لكن الثالث كان مناسباً تماماً. أخيراً، جربت أسرتهم. الأول كان قاسياً جداً، والثاني ناعماً جداً، لكن الثالث كان مناسباً تماماً، ونامت. عندما عاد الدببة إلى المنزل، وجدوا جولديلوكس في منزلهم. استيقظت وهربت، ولم تعد أبداً.",
            turkishContent = "Bir zamanlar Goldilocks adında küçük bir kız ormanda yürüyüşe çıktı ve üç ayıya ait bir ev buldu. İçeri girdi ve yulaf lapalarını denedi. İlk kase çok sıcaktı, ikincisi çok soğuktu, ama üçüncüsü tam kararındaydı. Sonra sandalyelerini denedi. İlki çok sertti, ikincisi çok yumuşaktı, ama üçüncüsü tam kararındaydı. Son olarak, yataklarını denedi. İlki çok sertti, ikincisi çok yumuşaktı, ama üçüncüsü tam kararındaydı ve uyuyakaldı. Ayılar eve geldiğinde, Goldilocks'u evlerinde buldular. Uyandı ve bir daha dönmemek üzere kaçtı."
        ),
        Story(
            title = "The Lion and the Mouse",
            content = "A sleeping lion was awakened by a little mouse running over him. The lion was about to eat the mouse, but the mouse pleaded, 'Please let me go, and I will help you one day.' The lion laughed at the idea of a mouse helping a lion, but he let the mouse go. Later, the lion was caught in a hunter's net. The mouse heard the lion's roars and came to help. The mouse chewed through the net and freed the lion. The lion learned that even the smallest friend can be a great help.",
            arabicContent = "أيقظ أسد نائم فأر صغير يجري فوقه. كان الأسد على وشك أكل الفأر، لكن الفأر توسل، 'من فضلك دعني أذهب، وسأساعدك يوماً ما.' ضحك الأسد من فكرة أن الفأر يساعد الأسد، لكنه سمح للفأر بالذهاب. لاحقاً، وقع الأسد في شباك صياد. سمع الفأر زئير الأسد وجاء للمساعدة. مضغ الفأر الشبكة وأطلق سراح الأسد. تعلم الأسد أن حتى أصغر صديق يمكن أن يكون مساعدة عظيمة.",
            turkishContent = "Uyuyan bir aslan, üzerinde koşan küçük bir fare tarafından uyandırıldı. Aslan fareyi yemek üzereydi, ama fare yalvardı, 'Lütfen beni bırak, bir gün sana yardım edeceğim.' Aslan bir farenin aslana yardım etme fikrine güldü, ama fareyi bıraktı. Daha sonra, aslan bir avcının ağına yakalandı. Fare aslanın kükremesini duydu ve yardıma geldi. Fare ağı kemirerek aslanı serbest bıraktı. Aslan, en küçük arkadaşın bile büyük bir yardım olabileceğini öğrendi."
        ),
        Story(
            title = "The Tortoise and the Hare",
            content = "A hare was boasting about how fast he could run. He laughed at the tortoise for being so slow. The tortoise challenged the hare to a race. The hare ran ahead and, seeing his lead, decided to take a nap. Meanwhile, the tortoise kept moving slowly but steadily. When the hare woke up, he saw the tortoise near the finish line. He ran as fast as he could, but it was too late. The tortoise had won the race. The hare learned that slow and steady wins the race.",
            arabicContent = "كان أرنب يتباهى بمدى سرعته في الجري. ضحك على السلحفاة لكونها بطيئة جداً. تحدت السلحفاة الأرنب إلى سباق. ركض الأرنب للأمام، ورأى تقدمه، فقرر أخذ قيلولة. في هذه الأثناء، استمرت السلحفاة في التحرك ببطء ولكن بثبات. عندما استيقظ الأرنب، رأى السلحفاة قريبة من خط النهاية. ركض بأسرع ما يمكن، لكن كان متأخراً جداً. فازت السلحفاة بالسباق. تعلم الأرنب أن البطيء والثابت يفوز بالسباق.",
            turkishContent = "Bir tavşan ne kadar hızlı koşabildiğiyle övünüyordu. Kaplumbağaya çok yavaş olduğu için güldü. Kaplumbağa tavşanı yarışmaya davet etti. Tavşan öne geçti ve önde olduğunu görünce biraz uyumaya karar verdi. Bu arada kaplumbağa yavaş ama kararlı bir şekilde ilerlemeye devam etti. Tavşan uyandığında, kaplumbağayı bitiş çizgisine yakın gördü. Olabildiğince hızlı koştu, ama çok geçti. Kaplumbağa yarışı kazandı. Tavşan, yavaş ve kararlı olanın yarışı kazanacağını öğrendi."
        )
    )

    // Get all stories
    fun getAllStories(): List<Story> = stories

    // Search for a story by title
    fun searchStory(query: String): List<Story> {
        return if (query.isBlank()) {
            stories
        } else {
            stories.filter { 
                it.title.contains(query, ignoreCase = true) ||
                it.content.contains(query, ignoreCase = true) ||
                it.arabicContent.contains(query, ignoreCase = true) ||
                it.turkishContent.contains(query, ignoreCase = true)
            }
        }
    }

    // Get favorite stories
    fun getFavoriteStories(): List<Story> {
        return stories.filter { it.isFavorite }
    }

    // Toggle favorite status for a story
    fun toggleFavorite(story: Story): Story {
        val index = stories.indexOf(story)
        if (index != -1) {
            return story.copy(isFavorite = !story.isFavorite)
        }
        return story
    }
} 