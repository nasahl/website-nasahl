package nasahl.generator;

import java.util.List;

record Category(String name, List<Skill> skills, boolean isVisible) {
}
