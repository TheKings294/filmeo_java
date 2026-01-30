function openPlatformModal() {
    document.getElementById('platformModal').classList.remove('hidden');
}

function closePlatformModal() {
    document.getElementById('platformModal').classList.add('hidden');
}

let platformSerieIndex =
    document.querySelectorAll('#platformSerieContainer > div').length;

function addPlatformSerie() {
    const platformId = document.getElementById('platformSelect').value;
    const endDate = document.getElementById('platformEndDate').value;

    if (!platformId) {
        alert('Please select a platform');
        return;
    }

    const container = document.getElementById('platformSerieContainer');

    container.insertAdjacentHTML('beforeend', `
        <div class="p-4 bg-neutral-900 rounded-lg border border-neutral-800 mb-4">
            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">

                <div>
                    <label class="block text-sm text-neutral-300 mb-2">Platform</label>
                    <select name="platformSerisId[${platformSerieIndex}].platformId"
                            class="w-full bg-neutral-800 text-white px-3 py-2 rounded-lg border border-neutral-700">
                        <option value="">-- Select --</option>
                        ${buildPlatformOptions(platformId)}
                    </select>
                </div>

                <div>
                    <label class="block text-sm text-neutral-300 mb-2">End Date</label>
                    <input type="date"
                           name="platformSerisId[${platformSerieIndex}].endDate"
                           value="${endDate ?? ''}"
                           class="w-full bg-neutral-800 text-white px-3 py-2 rounded-lg border border-neutral-700">
                </div>
            </div>

            <button type="button"
                    onclick="removePlatform(this)"
                    class="mt-3 text-sm text-red-500 hover:text-red-400">
                Remove platform
            </button>
        </div>
    `);

    platformSerieIndex++;
    closePlatformModal();
}

function buildPlatformOptions(selectedId) {
    let options = '';
    document.querySelectorAll('#platformSelect option').forEach(opt => {
        if (!opt.value) return;
        options += `
            <option value="${opt.value}" ${opt.value == selectedId ? 'selected' : ''}>
                ${opt.text}
            </option>`;
    });
    return options;
}

function removePlatform(button) {
    button.closest('.platform-seri-row').remove();
}
