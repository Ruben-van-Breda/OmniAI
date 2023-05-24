const { Configuration, OpenAIApi } = require("openai");

const API_KEY = process.env.DAVINCI || 'NO_API_KEY';

const configuration = new Configuration({
  apiKey: API_KEY,
});

const openai = new OpenAIApi(configuration);

async function prompt(promptMsg) {
  let response = 'Sorry, unfortunately, I am not able to answer your question right now.';

  try {
    const openaiResponse = await openai.createCompletion({
      model: 'text-davinci-002',
      prompt: promptMsg,
      temperature: 0.9,
      max_tokens: 150,
      top_p: 1,
      frequency_penalty: 0,
      presence_penalty: 0.6,
      stop: [' Human:', ' AI:'],
    });
    response = openaiResponse.data.choices[0].text.trim();
    return response
  } catch (e) {
    return response;
  }
}

async function imagePrompt(promptMsg) {
  try {
    const openaiResponse = await openai.createImage({
      prompt: promptMsg,
      n: 1,
      size: '1024x1024',
    });
    
    const imageUrl = openaiResponse.data.data[0].url;
    return imageUrl;
  } catch (e) {
    console.log(`Davinci failed to generate image response: ${e}`);
    return '/noimage_bot_default.jpg';
  }
}

module.exports = {
  Prompt: prompt,
  ImagePrompt: imagePrompt,
};
