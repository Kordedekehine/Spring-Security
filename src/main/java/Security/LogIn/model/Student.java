package Security.LogIn.model;

import com.sun.istack.NotNull;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(	name = "students")//,
//        uniqueConstraints = {
//                @UniqueConstraint(columnNames = "username"),
//                @UniqueConstraint(columnNames = "email")
       // })
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Student {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @NotBlank(message = "Customer Number cannot be empty")
        private Long id;

        @Size(min = 1, max = 255, message = "Name must not be too short or long")
        @NotNull
        private String lastName;

        @Size(min = 1, max = 255)
        @NotNull
        private String firstName;

        @Column
        private String username;

        @Column
        private String phoneNumber;
        @Column
        @Max(50)
        @Email
        private String email;
        @Column
        private String password;
       // @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
        //@JoinColumn
       @ManyToMany(fetch = FetchType.LAZY)
        private Set<Role> role = new HashSet<>();

        public Student() {
        }

        public Student(Long id, String lastName, String firstName, String username,
                       String phoneNumber, String email, String password) {
                this.id = id;
                this.lastName = lastName;
                this.firstName = firstName;
                this.username = username;
                this.phoneNumber = phoneNumber;
                this.email = email;
                this.password = password;
                this.role = role;
        }

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getLastName() {
                return lastName;
        }

        public void setLastName(String lastName) {
                this.lastName = lastName;
        }

        public String getFirstName() {
                return firstName;
        }

        public void setFirstName(String firstName) {
                this.firstName = firstName;
        }

        public String getUsername() {
                return username;
        }

        public void setUsername(String username) {
                this.username = username;
        }

        public String getPhoneNumber() {
                return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
                this.phoneNumber = phoneNumber;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public Set<Role> getRole() {
                return role;
        }

        public void setRole(Set<Role> role) {
                this.role = role;
        }
}
