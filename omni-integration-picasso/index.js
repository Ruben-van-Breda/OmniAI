const express = require('express');
const bodyParser = require('body-parser');
const { Prompt, ImagePrompt } = require('./AI_BOT');

const app = express();
const port = process.env.PORT || 8083;

app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

app.get('/', (req, res) => {
  res.send('(Picasso)Davcinci-002 Bot is running');
});

app.post('/', async (req, res) => {
  console.log('Query received');
  const query = req.body.query;
  console.log(query);
  const [response, prompts] = await Prompt(query);
  const responseStr = response.trim();
  console.log('Sending response: ' + responseStr);
  console.log('Prompts: ' + JSON.stringify(prompts));
  res.send(responseStr);
});

app.post('/chat/query', async (req, res) => {
  console.log('Query received');
  try {
    const query = req.body.query;
    const response = await Prompt(query);

    const responseData = {
      reference: 'Picasso',
      queryResponse: response,
    };


    res.json(responseData);


  } catch (e) {
    res.json(e);
  }
});

app.post('/chat/image', async (req, res) => {
  console.log('Image Query received');
  const query = req.body.query;
  const response = await ImagePrompt(query);
  const responseStr = response;

  // Load default image
  if (responseStr === '') {
    responseStr = 'https://i.imgur.com/2Q2Qz0I.png';
  }

  const responseData = {
    reference: 'Picasso',
    queryResponse: responseStr,
  };

  res.json(responseData);
});

app.listen(port, () => {
  console.log(`Starting Davinci server on port: ${port}`);
});
