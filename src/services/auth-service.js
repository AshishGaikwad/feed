import config from './config.json';

export default async function sentOTP(pMobile) {
  var URL = config.BaseURL + config.SendOTPURL;
  var body ={
    mobile: pMobile,
  }

  console.log(body);
  const response = await fetch(URL, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(body),
  })
  const data = await response.json();
  console.log("users ", data);
  return data;
}


export  async function verifyOTP(pOTP,pRefNo,pMobile) {
    var URL = config.BaseURL + config.VeriyOTPURL;
    var body ={
      mobile: pMobile,
      otp: pOTP,
      refno: pRefNo,
    }
  
    console.log(body);
    const response = await fetch(URL, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(body),
    })
    const data = await response.json();
    console.log("users ", data);
    return data;
  }


  export  async function getUserDetails(pMobile,pToken) {
    var URL = config.BaseURL + config.GetUserDetailsURL;
    URL = URL.replace("{pMobileNo}",pMobile);
    const response = await fetch(URL, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization':'Bearer '+pToken
      },
    })
    const data = await response.json();
    console.log("users ", data);
    return data;
  }