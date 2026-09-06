import os

root_dir = r"c:\Users\naska\OneDrive\Documents\GitHub\openwhispr-android\android-app"

modules = [
    {
        "path": "core/common",
        "namespace": "com.openwhispr.android.core.common",
        "plugins": ["alias(libs.plugins.android.library)", "alias(libs.plugins.kotlin.android)", "alias(libs.plugins.hilt)"],
        "dependencies": ["implementation(libs.androidx.appcompat)", "implementation(libs.androidx.core.ktx)", "implementation(libs.kotlinx.coroutines.android)", "implementation(libs.hilt.android)", "ksp(libs.hilt.compiler)"],
        "is_feature": False,
        "placeholder": None
    },
    {
        "path": "core/database",
        "namespace": "com.openwhispr.android.core.database",
        "plugins": ["alias(libs.plugins.android.library)", "alias(libs.plugins.kotlin.android)", "alias(libs.plugins.ksp)", "alias(libs.plugins.hilt)"],
        "dependencies": ["implementation(libs.room.runtime)", "implementation(libs.room.ktx)", "ksp(libs.room.compiler)", "implementation(libs.androidx.core.ktx)", "implementation(libs.kotlinx.coroutines.android)", "implementation(libs.hilt.android)", "ksp(libs.hilt.compiler)"],
        "is_feature": False,
        "placeholder": "OpenWhisprDatabase.kt"
    },
    {
        "path": "core/network",
        "namespace": "com.openwhispr.android.core.network",
        "plugins": ["alias(libs.plugins.android.library)", "alias(libs.plugins.kotlin.android)", "alias(libs.plugins.ksp)", "alias(libs.plugins.hilt)"],
        "dependencies": ["implementation(libs.retrofit)", "implementation(libs.retrofit.moshi)", "implementation(libs.okhttp)", "implementation(libs.okhttp.logging)", "implementation(libs.okhttp.sse)", "implementation(libs.moshi)", "ksp(libs.moshi.kotlin.codegen)", "implementation(libs.kotlinx.coroutines.android)", "implementation(libs.hilt.android)", "ksp(libs.hilt.compiler)"],
        "is_feature": False,
        "placeholder": "ApiClient.kt"
    },
    {
        "path": "core/audio",
        "namespace": "com.openwhispr.android.core.audio",
        "plugins": ["alias(libs.plugins.android.library)", "alias(libs.plugins.kotlin.android)", "alias(libs.plugins.hilt)"],
        "dependencies": ["implementation(libs.androidx.core.ktx)", "implementation(libs.kotlinx.coroutines.android)", "implementation(libs.lifecycle.service)", "implementation(libs.hilt.android)", "ksp(libs.hilt.compiler)"],
        "is_feature": False,
        "placeholder": "AudioManager.kt"
    },
    {
        "path": "core/ai",
        "namespace": "com.openwhispr.android.core.ai",
        "plugins": ["alias(libs.plugins.android.library)", "alias(libs.plugins.kotlin.android)", "alias(libs.plugins.ksp)", "alias(libs.plugins.hilt)"],
        "dependencies": ["implementation(project(\":core:common\"))", "implementation(project(\":core:network\"))", "implementation(libs.kotlinx.coroutines.android)", "implementation(libs.okhttp)", "implementation(libs.okhttp.sse)", "implementation(libs.moshi)", "ksp(libs.moshi.kotlin.codegen)", "implementation(libs.hilt.android)", "ksp(libs.hilt.compiler)"],
        "is_feature": False,
        "placeholder": "AiManager.kt"
    },
    {
        "path": "core/whisper",
        "namespace": "com.openwhispr.android.core.whisper",
        "plugins": ["alias(libs.plugins.android.library)", "alias(libs.plugins.kotlin.android)", "alias(libs.plugins.hilt)"],
        "dependencies": ["implementation(project(\":core:common\"))", "implementation(project(\":core:audio\"))", "implementation(libs.androidx.core.ktx)", "implementation(libs.kotlinx.coroutines.android)", "implementation(libs.hilt.android)", "ksp(libs.hilt.compiler)"],
        "is_feature": False,
        "placeholder": "WhisperEngine.kt"
    },
    {
        "path": "core/model-manager",
        "namespace": "com.openwhispr.android.core.modelmanager",
        "plugins": ["alias(libs.plugins.android.library)", "alias(libs.plugins.kotlin.android)", "alias(libs.plugins.hilt)"],
        "dependencies": ["implementation(project(\":core:common\"))", "implementation(project(\":core:network\"))", "implementation(libs.androidx.core.ktx)", "implementation(libs.kotlinx.coroutines.android)", "implementation(libs.work.runtime)", "implementation(libs.okhttp)", "implementation(libs.hilt.android)", "ksp(libs.hilt.compiler)"],
        "is_feature": False,
        "placeholder": "ModelManager.kt"
    },
    {
        "path": "feature/dictation",
        "namespace": "com.openwhispr.android.feature.dictation",
        "plugins": ["alias(libs.plugins.android.library)", "alias(libs.plugins.kotlin.android)", "alias(libs.plugins.hilt)"],
        "dependencies": ["implementation(project(\":core:common\"))", "implementation(project(\":core:audio\"))", "implementation(project(\":core:whisper\"))", "implementation(project(\":core:ai\"))", "implementation(libs.androidx.appcompat)", "implementation(libs.androidx.fragment.ktx)", "implementation(libs.androidx.constraintlayout)", "implementation(libs.material)", "implementation(libs.lifecycle.viewmodel)", "implementation(libs.lifecycle.livedata)", "implementation(libs.navigation.fragment)", "implementation(libs.navigation.ui)", "implementation(libs.hilt.android)", "ksp(libs.hilt.compiler)"],
        "is_feature": True,
        "placeholder": "DictationFragment.kt"
    },
    {
        "path": "feature/assistant",
        "namespace": "com.openwhispr.android.feature.assistant",
        "plugins": ["alias(libs.plugins.android.library)", "alias(libs.plugins.kotlin.android)", "alias(libs.plugins.hilt)"],
        "dependencies": ["implementation(project(\":core:common\"))", "implementation(project(\":core:ai\"))", "implementation(project(\":core:audio\"))", "implementation(project(\":core:whisper\"))", "implementation(libs.androidx.appcompat)", "implementation(libs.androidx.fragment.ktx)", "implementation(libs.androidx.constraintlayout)", "implementation(libs.material)", "implementation(libs.lifecycle.viewmodel)", "implementation(libs.lifecycle.livedata)", "implementation(libs.navigation.fragment)", "implementation(libs.navigation.ui)", "implementation(libs.hilt.android)", "ksp(libs.hilt.compiler)"],
        "is_feature": True,
        "placeholder": "AssistantFragment.kt"
    },
    {
        "path": "feature/meetings",
        "namespace": "com.openwhispr.android.feature.meetings",
        "plugins": ["alias(libs.plugins.android.library)", "alias(libs.plugins.kotlin.android)", "alias(libs.plugins.hilt)"],
        "dependencies": ["implementation(project(\":core:common\"))", "implementation(project(\":core:audio\"))", "implementation(project(\":core:whisper\"))", "implementation(project(\":core:ai\"))", "implementation(project(\":core:database\"))", "implementation(libs.androidx.appcompat)", "implementation(libs.androidx.fragment.ktx)", "implementation(libs.androidx.constraintlayout)", "implementation(libs.material)", "implementation(libs.lifecycle.viewmodel)", "implementation(libs.lifecycle.livedata)", "implementation(libs.navigation.fragment)", "implementation(libs.navigation.ui)", "implementation(libs.hilt.android)", "ksp(libs.hilt.compiler)"],
        "is_feature": True,
        "placeholder": "MeetingsFragment.kt"
    },
    {
        "path": "feature/notes",
        "namespace": "com.openwhispr.android.feature.notes",
        "plugins": ["alias(libs.plugins.android.library)", "alias(libs.plugins.kotlin.android)", "alias(libs.plugins.hilt)"],
        "dependencies": ["implementation(project(\":core:common\"))", "implementation(project(\":core:database\"))", "implementation(project(\":core:ai\"))", "implementation(project(\":core:network\"))", "implementation(libs.androidx.appcompat)", "implementation(libs.androidx.fragment.ktx)", "implementation(libs.androidx.constraintlayout)", "implementation(libs.material)", "implementation(libs.lifecycle.viewmodel)", "implementation(libs.lifecycle.livedata)", "implementation(libs.navigation.fragment)", "implementation(libs.navigation.ui)", "implementation(libs.androidx.recyclerview)", "implementation(libs.hilt.android)", "ksp(libs.hilt.compiler)"],
        "is_feature": True,
        "placeholder": "NotesFragment.kt"
    },
    {
        "path": "feature/settings",
        "namespace": "com.openwhispr.android.feature.settings",
        "plugins": ["alias(libs.plugins.android.library)", "alias(libs.plugins.kotlin.android)", "alias(libs.plugins.hilt)"],
        "dependencies": ["implementation(project(\":core:common\"))", "implementation(project(\":core:database\"))", "implementation(project(\":core:network\"))", "implementation(project(\":core:modelmanager\"))", "implementation(libs.androidx.appcompat)", "implementation(libs.androidx.fragment.ktx)", "implementation(libs.androidx.preference.ktx)", "implementation(libs.material)", "implementation(libs.androidx.constraintlayout)", "implementation(libs.androidx.recyclerview)", "implementation(libs.lifecycle.viewmodel)", "implementation(libs.lifecycle.livedata)", "implementation(libs.navigation.fragment)", "implementation(libs.navigation.ui)", "implementation(libs.hilt.android)", "ksp(libs.hilt.compiler)", "implementation(libs.security.crypto)"],
        "is_feature": True,
        "placeholder": "SettingsFragment.kt"
    },
    {
        "path": "feature/chat",
        "namespace": "com.openwhispr.android.feature.chat",
        "plugins": ["alias(libs.plugins.android.library)", "alias(libs.plugins.kotlin.android)", "alias(libs.plugins.hilt)"],
        "dependencies": ["implementation(project(\":core:common\"))", "implementation(project(\":core:ai\"))", "implementation(project(\":core:database\"))", "implementation(libs.androidx.appcompat)", "implementation(libs.androidx.fragment.ktx)", "implementation(libs.androidx.constraintlayout)", "implementation(libs.material)", "implementation(libs.lifecycle.viewmodel)", "implementation(libs.lifecycle.livedata)", "implementation(libs.navigation.fragment)", "implementation(libs.navigation.ui)", "implementation(libs.androidx.recyclerview)", "implementation(libs.hilt.android)", "ksp(libs.hilt.compiler)"],
        "is_feature": True,
        "placeholder": "ChatFragment.kt"
    }
]

manifest_template = '''<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">
</manifest>
'''

def get_gradle_template(module):
    plugins_str = "\\n    ".join(module['plugins'])
    deps_str = "\\n    ".join(module['dependencies'])
    
    view_binding_str = ""
    if module['is_feature']:
        view_binding_str = """
    buildFeatures {
        viewBinding = true
    }"""
    
    return f"""plugins {{
    {plugins_str}
}}

android {{
    namespace = "{module['namespace']}"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {{
        minSdk = libs.versions.minSdk.get().toInt()
    }}

    compileOptions {{
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }}
    
    kotlinOptions {{
        jvmTarget = "17"
    }}{view_binding_str}
}}

dependencies {{
    {deps_str}
}}
"""

for module in modules:
    mod_path = os.path.join(root_dir, module['path'])
    
    # create module dir
    os.makedirs(mod_path, exist_ok=True)
    
    # write build.gradle.kts
    gradle_path = os.path.join(mod_path, "build.gradle.kts")
    with open(gradle_path, 'w', encoding='utf-8') as f:
        f.write(get_gradle_template(module))
        
    # write AndroidManifest.xml
    manifest_dir = os.path.join(mod_path, "src", "main")
    os.makedirs(manifest_dir, exist_ok=True)
    manifest_path = os.path.join(manifest_dir, "AndroidManifest.xml")
    with open(manifest_path, 'w', encoding='utf-8') as f:
        f.write(manifest_template)
        
    # write placeholder .kt file
    if module['placeholder']:
        package_dir = os.path.join(manifest_dir, "java", *module['namespace'].split('.'))
        os.makedirs(package_dir, exist_ok=True)
        placeholder_path = os.path.join(package_dir, module['placeholder'])
        class_name = module['placeholder'].replace(".kt", "")
        with open(placeholder_path, 'w', encoding='utf-8') as f:
            f.write(f"package {module['namespace']}\\n\\nclass {class_name} {{\\n}}\\n")

print("All modules created successfully.")
