function allCheck() {
  const id = document.getElementById("id").value;
  const pw = document.getElementById("pw").value;
  const confirm = document.getElementById("confirm").value;
  const userName = document.getElementById("userName").value;

  if (!id || !pw || !confirm || !userName) {
    alert("모든 항목을 입력해주세요.");
    return;
  }

  if (pw !== confirm) {
    alert("비밀번호가 일치하지 않습니다.");
    return;
  }

  console.log("✅ 모든 조건 통과 → submit 실행");
  document.getElementById("f").submit();
}

function pwCheck() {
  const pw = document.getElementById("pw").value;
  const confirm = document.getElementById("confirm").value;
  const label = document.getElementById("label");

  if (pw === confirm) {
    label.innerText = "✅ 일치";
    label.style.color = "green";
  } else {
    label.innerText = "❌ 불일치";
    label.style.color = "red";
  }
}


function loginCheck(){
	let id = document.getElementById('id');
	let pw = document.getElementById('pw');
	
	if(id.value == ""){
		alert('아이디는 필수 항목입니다.');
	}else if(pw.value == ""){
		alert('비밀번호는 필수 항목입니다.');
	}else{
		var f = document.getElementById('f');
		f.submit();
	}
}


